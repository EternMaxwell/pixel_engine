package com.maxwell_dev.pixel_engine.util;

import org.lwjgl.util.opus.OGGPacket;

import java.util.*;
import java.util.function.IntFunction;

public class Util {

    public static class mesh {
        public static float[][] line_simplification(float[][] vertices_in, float maxHeight) {
            float[][] line = new float[][]{vertices_in[0], vertices_in[vertices_in.length - 1]};
            float height = 0;
            int maxIndex = 0;
            for (int i = 1; i < vertices_in.length - 1; i++) {
                float temp = distance(line, vertices_in[i]);
                if (temp > height && temp > maxHeight) {
                    height = temp;
                    maxIndex = i;
                }
            }
            if (maxIndex == 0) {
                return new float[][]{vertices_in[0], vertices_in[vertices_in.length - 1]};
            } else {
                float[][] left = line_simplification(Arrays.copyOfRange(vertices_in, 0, maxIndex + 1), maxHeight);
                float[][] right = line_simplification(Arrays.copyOfRange(vertices_in, maxIndex, vertices_in.length), maxHeight);
                float[][] result = new float[left.length + right.length - 1][2];
                System.arraycopy(left, 0, result, 0, left.length);
                System.arraycopy(right, 1, result, left.length, right.length - 1);
                return result;
            }
        }

        public static float distance(float[][] line, float[] point) {
            float x1 = line[0][0];
            float y1 = line[0][1];
            float x2 = line[1][0];
            float y2 = line[1][1];
            float x = point[0];
            float y = point[1];
            float A = x - x1;
            float B = y - y1;
            float C = x2 - x1;
            float D = y2 - y1;
            float dot = A * C + B * D;
            float len_sq = C * C + D * D;
            float param = -1;
            if (len_sq != 0) {
                param = dot / len_sq;
            }
            float xx, yy;
            if (param < 0) {
                xx = x1;
                yy = y1;
            } else if (param > 1) {
                xx = x2;
                yy = y2;
            } else {
                xx = x1 + param * C;
                yy = y1 + param * D;
            }
            float dx = x - xx;
            float dy = y - yy;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        public static <T> Set<T[][]> split(T[][] grid, IntFunction<T[][]> generator, IntFunction<T[]> rowGenerator) {
            Set<T[][]> result = new HashSet<>();
            Set<T[][]> temp = new HashSet<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != null) {
                        temp.clear();
                        if (result.isEmpty()) {
                            T[][] newTarget = generator.apply(grid.length);
                            for (int k = 0; k < newTarget.length; k++) {
                                newTarget[k] = rowGenerator.apply(grid[i].length);
                            }
                            add(newTarget, grid[i][j], i, j);
                            result.add(newTarget);
                            continue;
                        }
                        for (T[][] target : result) {
                            if (hasNearby(target, i, j)) {
                                temp.add(target);
                            }
                        }
                        T[][] newTarget = generator.apply(grid.length);
                        for (int k = 0; k < newTarget.length; k++) {
                            newTarget[k] = rowGenerator.apply(grid[i].length);
                        }
                        add(newTarget, grid[i][j], i, j);
                        for (T[][] target : temp) {
                            result.remove(target);
                            combine(newTarget, target);
                        }
                        result.add(newTarget);
                    }
                }
            }
            return result;
        }

        public static <T> Set<T[][]> splitWithDiagonal(T[][] grid, IntFunction<T[][]> generator, IntFunction<T[]> rowGenerator) {
            Set<T[][]> result = new HashSet<>();
            Set<T[][]> temp = new HashSet<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != null) {
                        temp.clear();
                        if (result.isEmpty()) {
                            T[][] newTarget = generator.apply(grid.length);
                            for (int k = 0; k < newTarget.length; k++) {
                                newTarget[k] = rowGenerator.apply(grid[i].length);
                            }
                            add(newTarget, grid[i][j], i, j);
                            result.add(newTarget);
                            continue;
                        }
                        for (T[][] target : result) {
                            if (hasNearby(target, i, j) || hasDiagonal(target, i, j)) {
                                temp.add(target);
                            }
                        }
                        T[][] newTarget = generator.apply(grid.length);
                        for (int k = 0; k < newTarget.length; k++) {
                            newTarget[k] = rowGenerator.apply(grid[i].length);
                        }
                        add(newTarget, grid[i][j], i, j);
                        for (T[][] target : temp) {
                            result.remove(target);
                            combine(newTarget, target);
                        }
                        result.add(newTarget);
                    }
                }
            }
            return result;
        }

        public static <T> Set<Object[][]> holes_in_grid_single(T[][] grid){
            Object[][] negated = new Object[grid.length][grid[0].length];
            Object any = new Object();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    negated[i][j] = grid[i][j] == null ? any : null;
                }
            }
            Set<Object[][]> result = splitWithDiagonal(negated, Object[][]::new, Object[]::new);
            List<int[]> posOutSide = new LinkedList<>();
            for(int i: new int[]{0, grid.length - 1}){
                for(int j = 0; j < grid[i].length; j++){
                    if(grid[i][j] == null){
                        posOutSide.add(new int[]{i, j});
                    }
                }
            }
            for(int j: new int[]{0, grid[0].length - 1}){
                for(int i = 0; i < grid.length; i++){
                    if(grid[i][j] == null){
                        posOutSide.add(new int[]{i, j});
                    }
                }
            }
            if (!posOutSide.isEmpty()) {
                List<Object[][]> resultList = result.stream().toList();
                LinkedList<Object[][]> resultListImmovable = new LinkedList<>(resultList);
                for (Object[][] temp : resultListImmovable) {
                    for (int[] pos : posOutSide) {
                        if (temp[pos[0]][pos[1]] != null) {
                            result.remove(temp);
                        }
                    }
                }
            }
            return result;
        }

        public static <T> Set<float[][]> holes_outline_single(T[][] grid, float pixel_size){
            Set<float[][]> result = new HashSet<>();
            Set<Object[][]> holes = holes_in_grid_single(grid);
            for(Object[][] singleHole : holes){
                float[][] outline = marching_squares_outline_single(singleHole, pixel_size);
                for(int i = 0; i < outline.length/2; i++){
                    float[] temp = outline[i];
                    outline[i] = outline[outline.length-i-1];
                    outline[outline.length-1-i] = temp;
                }
                result.add(outline);
            }
            return result;
        }

        private static <T> boolean hasNearby(T[][] target, int x, int y) {
            return (x > 0 && target[x - 1][y] != null) || (y > 0 && target[x][y - 1] != null) || (x < target.length - 1 && target[x + 1][y] != null) || (y < target[x].length - 1 && target[x][y + 1] != null);
        }

        private static <T> boolean hasDiagonal(T[][] target, int x, int y) {
            return (x > 0 && y > 0 && target[x - 1][y - 1] != null) || (x < target.length - 1 && y > 0 && target[x + 1][y - 1] != null) || (x < target.length - 1 && y < target[x].length - 1 && target[x + 1][y + 1] != null) || (x > 0 && y < target[x].length - 1 && target[x - 1][y + 1] != null);
        }

        private static <T> void add(T[][] target, T addition, int x, int y) {
            target[x][y] = addition;
        }

        private static <T> void combine(T[][] target, T[][] addition) {
            for (int i = 0; i < target.length; i++) {
                for (int j = 0; j < target[i].length; j++) {
                    target[i][j] = target[i][j] != null ? target[i][j] : addition[i][j];
                }
            }
        }

        public static <T> Set<float[][]> marching_squares_outline(T[][] grid, float pixel_size, IntFunction<T[][]> generator, IntFunction<T[]> rowGenerator) {
            Set<T[][]> split = split(grid, generator, rowGenerator);
            Set<float[][]> result = new HashSet<>();
            for (T[][] target : split) {
                result.add(marching_squares_outline_single(target, pixel_size));
            }
            return result;
        }

        public static <T> float[][] marching_squares_outline_single(T[][] grid, float pixel_size) {
            List<float[]> result = new LinkedList<>();
            List<int[]> resultInInt = new LinkedList<>();
            int[] dir = new int[]{0, 1};
            int startX = 0;
            int startY = 0;
            int currentX = 0;
            int currentY = 0;
            boolean empty = true;
            a:
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != null) {
                        startX = i;
                        startY = j;
                        currentX = i;
                        currentY = j;
                        empty = false;
                        break a;
                    }
                }
            }
            if (empty) {
                return new float[0][0];
            }
            do {
                currentY += dir[1];
                currentX += dir[0];
                resultInInt.add(new int[]{currentX, currentY});
                dirExpect(grid, currentX, currentY, dir);
            } while (currentY != startY || currentX != startX);
            for (int[] pos : resultInInt) {
                result.add(new float[]{pos[0] * pixel_size, pos[1] * pixel_size});
            }
            return result.toArray(new float[0][0]);
        }

        private static void turnLeft(int[] dir) {
            int temp = dir[0];
            dir[0] = -dir[1];
            dir[1] = temp;
        }

        private static void turnRight(int[] dir) {
            int temp = dir[0];
            dir[0] = dir[1];
            dir[1] = -temp;
        }

        private static <T> boolean lb(T[][] grid, int x, int y, int[] dir) {
            try {
                return x > 0 && y > 0 && grid[x - 1][y - 1] != null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        private static <T> boolean rb(T[][] grid, int x, int y, int[] dir) {
            try {
                return x < grid.length && y > 0 && grid[x][y - 1] != null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        private static <T> boolean rt(T[][] grid, int x, int y, int[] dir) {
            try {
                return x < grid.length && y < grid[x].length && grid[x][y] != null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        private static <T> boolean lt(T[][] grid, int x, int y, int[] dir) {
            try {
                return x > 0 && y < grid[x - 1].length && grid[x - 1][y] != null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        private static boolean up(int[] dir) {
            return dir[0] == 0 && dir[1] == 1;
        }

        private static boolean down(int[] dir) {
            return dir[0] == 0 && dir[1] == -1;
        }

        private static boolean left(int[] dir) {
            return dir[0] == -1 && dir[1] == 0;
        }

        private static boolean right(int[] dir) {
            return dir[0] == 1 && dir[1] == 0;
        }

        private static <T> void dirExpect(T[][] grid, int x, int y, int[] dir) {
            if (up(dir)) {
                if (rb(grid, x, y, dir)) {
                    if (rt(grid, x, y, dir)) {
                        if (lt(grid, x, y, dir)) {
                            turnLeft(dir);
                        }
                    } else {
                        turnRight(dir);
                    }
                }
            } else if (left(dir)) {
                if (rt(grid, x, y, dir)) {
                    if (lt(grid, x, y, dir)) {
                        if (lb(grid, x, y, dir)) {
                            turnLeft(dir);
                        }
                    } else {
                        turnRight(dir);
                    }
                }
            } else if (down(dir)) {
                if (lt(grid, x, y, dir)) {
                    if (lb(grid, x, y, dir)) {
                        if (rb(grid, x, y, dir)) {
                            turnLeft(dir);
                        }
                    } else {
                        turnRight(dir);
                    }
                }
            } else if (right(dir)) {
                if (lb(grid, x, y, dir)) {
                    if (rb(grid, x, y, dir)) {
                        if (rt(grid, x, y, dir)) {
                            turnLeft(dir);
                        }
                    } else {
                        turnRight(dir);
                    }
                }
            }
        }
    }
}
