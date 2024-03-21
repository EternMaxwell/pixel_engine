package com.maxwell_dev.pixel_engine.util;

import java.util.*;
import java.util.function.IntFunction;

public class Util {
    public static float[][] line_simplification(float[][] vertices_in, float maxHeight) {
        float[][] line = new float[][]{vertices_in[0], vertices_in[vertices_in.length-1]};
        float height = 0;
        int maxIndex = 0;
        for(int i = 1; i < vertices_in.length - 1; i++) {
            float temp = distance(line, vertices_in[i]);
            if(temp > height && temp > maxHeight) {
                height = temp;
                maxIndex = i;
            }
        }
        if(maxIndex == 0) {
            return new float[][]{vertices_in[0], vertices_in[vertices_in.length-1]};
        }else {
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
        if(len_sq != 0) {
            param = dot / len_sq;
        }
        float xx, yy;
        if(param < 0) {
            xx = x1;
            yy = y1;
        } else if(param > 1) {
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
                        for(int k = 0; k < newTarget.length; k++) {
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
                    for(int k = 0; k < newTarget.length; k++) {
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

    private static <T> boolean hasNearby(T[][] target, int x, int y) {
        return (x > 0 && target[x - 1][y] != null) || (y > 0 && target[x][y - 1] != null) || (x < target.length - 1 && target[x + 1][y] != null) || (y < target[x].length - 1 && target[x][y + 1] != null);
    }

    private static <T> void add(T[][] target, T addition, int x, int y) {
        target[x][y] = addition;
    }

    private static <T> void combine(T[][] target, T[][] addition) {
        for(int i = 0; i < target.length; i++) {
            for(int j = 0; j < target[i].length; j++) {
                target[i][j] = target[i][j] != null ? target[i][j] : addition[i][j];
            }
        }
    }

    public static <T> Set<float[][]> marching_squares(T[][] grid, float pixel_size, IntFunction<T[][]> generator, IntFunction<T[]> rowGenerator) {
        Set<T[][]> split = split(grid, generator, rowGenerator);
        Set<float[][]> result = new HashSet<>();
        for(T[][] target : split) {
            result.add(marching_squares_single(target, pixel_size));
        }
        return result;
    }

    public static <T> float[][] marching_squares_single(T[][] grid, float pixel_size) {
        List<float[]> result = new LinkedList<>();
        List<int[]> resultInInt = new LinkedList<>();
        int[] dir = new int[]{0,1};
        int startX = 0;
        int startY = 0;
        int currentX = 0;
        int currentY = 0;
        boolean empty = true;
        a: for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] != null){
                    startX = i;
                    startY = j;
                    currentX = i;
                    currentY = j;
                    empty = false;
                    break a;
                }
            }
        }
        if(empty){
            return new float[0][0];
        }
        do{
            currentY += dir[1];
            currentX += dir[0];
            resultInInt.add(new int[]{currentX, currentY});
            dirExpect(grid, currentX, currentY, dir);
        }while(currentY != startY || currentX != startX);
        for(int[] pos : resultInInt){
            result.add(new float[]{pos[0] * pixel_size, pos[1] * pixel_size});
        }
        return result.toArray(new float[0][0]);
    }

    private static void left(int[] dir){
        int temp = dir[0];
        dir[0] = -dir[1];
        dir[1] = temp;
    }

    private static void right(int[] dir){
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
        try{
            return x < grid.length && y > 0 && grid[x][y - 1] != null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static <T> boolean rt(T[][] grid, int x, int y, int[] dir) {
        try{
            return x < grid.length && y < grid[x].length && grid[x][y] != null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static <T> boolean lt(T[][] grid, int x, int y, int[] dir) {
        try{
            return x > 0 && y < grid[x-1].length && grid[x - 1][y] != null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static final int patternNone = 0;
    private static final int patternAll = 1;
    private static final int patternLeft = 2;
    private static final int patternRight = 3;
    private static final int patternTop = 4;
    private static final int patternBottom = 5;
    private static final int patternTopLeft = 6;
    private static final int patternTopRight = 7;
    private static final int patternBottomRight = 8;
    private static final int patternBottomLeft = 9;
    private static final int patternNoTopLeft = 10;
    private static final int patternNoTopRight = 11;
    private static final int patternNoBottomRight = 12;
    private static final int patternNoBottomLeft = 13;

    private static<T> void dirExpect(T[][] grid, int x, int y, int[] dir) {
        int pattern = 0;
        if(lb(grid, x, y, dir) && rb(grid, x, y, dir) && rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternAll;
        }
        if(lb(grid, x, y, dir) && rb(grid, x, y, dir) && rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternNoTopLeft;
        }
        if(lb(grid, x, y, dir) && rb(grid, x, y, dir) && !rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternNoTopRight;
        }
        if(lb(grid, x, y, dir) && !rb(grid, x, y, dir) && rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternNoBottomRight;
        }
        if(!lb(grid, x, y, dir) && rb(grid, x, y, dir) && rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternNoBottomLeft;
        }
        if(!lb(grid, x, y, dir) && !rb(grid, x, y, dir) && rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternTop;
        }
        if(!lb(grid, x, y, dir) && rb(grid, x, y, dir) && rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternRight;
        }
        if(lb(grid, x, y, dir) && rb(grid, x, y, dir) && !rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternBottom;
        }
        if(lb(grid, x, y, dir) && !rb(grid, x, y, dir) && !rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternLeft;
        }
        if(!lb(grid, x, y, dir) && !rb(grid, x, y, dir) && !rt(grid, x, y, dir) && lt(grid, x, y, dir)) {
            pattern = patternTopLeft;
        }
        if(!lb(grid, x, y, dir) && !rb(grid, x, y, dir) && rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternTopRight;
        }
        if(!lb(grid, x, y, dir) && rb(grid, x, y, dir) && !rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternBottomRight;
        }
        if(lb(grid, x, y, dir) && !rb(grid, x, y, dir) && !rt(grid, x, y, dir) && !lt(grid, x, y, dir)) {
            pattern = patternBottomLeft;
        }
        switch (pattern) {
            case patternAll:
                dir[0] = 0;
                dir[1] = 1;
                break;
            case patternLeft:
                dir[0] = 0;
                dir[1] = -1;
                break;
            case patternRight:
                dir[0] = 0;
                dir[1] = 1;
                break;
            case patternTop:
                dir[0] = -1;
                dir[1] = 0;
                break;
            case patternBottom:
                dir[0] = 1;
                dir[1] = 0;
                break;
            case patternTopLeft:
                dir[0] = -1;
                dir[1] = 0;
                break;
            case patternTopRight:
                dir[0] = 0;
                dir[1] = 1;
                break;
            case patternBottomRight:
                dir[0] = 1;
                dir[1] = 0;
                break;
            case patternBottomLeft:
                dir[0] = 0;
                dir[1] = -1;
                break;
            case patternNoTopLeft:
                dir[0] = 0;
                dir[1] = 1;
                break;
            case patternNoTopRight:
                dir[0] = 1;
                dir[1] = 0;
                break;
            case patternNoBottomRight:
                dir[0] = 0;
                dir[1] = -1;
                break;
            case patternNoBottomLeft:
                dir[0] = -1;
                dir[1] = 0;
                break;
            default:
                dir[0] = 0;
                dir[1] = 1;
                break;
        }
    }
}
