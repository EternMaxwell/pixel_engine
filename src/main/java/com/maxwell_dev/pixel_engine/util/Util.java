package com.maxwell_dev.pixel_engine.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static <T> Set<T[][]> split(T[][] grid, T[][] emptyGridForCopy) {
        Set<T[][]> result = new HashSet<>();
        Set<T[][]> temp = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    if(result.isEmpty()){
                        T[][] newTarget = Arrays.copyOf(emptyGridForCopy, emptyGridForCopy.length);
                        add(newTarget, grid[i][j], i, j);
                        result.add(newTarget);
                        continue;
                    }
                    for(T[][] target : result) {
                        if (hasNearby(target, i, j)) {
                            temp.add(target);
                        }
                    }
                    if(temp.isEmpty()) {
                        T[][] newTarget = Arrays.copyOf(emptyGridForCopy, emptyGridForCopy.length);
                        add(newTarget, grid[i][j], i, j);
                        result.add(newTarget);
                    }else {
                        List<T[][]> tempList = temp.stream().toList();
                        T[][] first = tempList.get(0);
                        add(first, grid[i][j], i, j);
                        for (int k = 1; k < tempList.size(); k++) {
                            combine(first, tempList.get(k));
                            result.remove(tempList.get(k));
                        }
                    }
                }
            }
        }
        return result;
    }

    private static <T> boolean hasNearby(T[][] target, int x, int y) {
        boolean result = false;
        if (x > 0) {
            result = result || target[x - 1][y] != null;
        }
        if (x < target.length - 1) {
            result = result || target[x + 1][y] != null;
        }
        if (y > 0) {
            result = result || target[x][y - 1] != null;
        }
        if (y < target[x].length - 1) {
            result = result || target[x][y + 1] != null;
        }
        return result;
    }

    private static <T> void add(T[][] target, T addition, int x, int y) {
        target[x][y] = addition;
    }

    private static <T> void combine(T[][] target, T[][] addition) {
        for(int i = 0; i < target.length; i++) {
            for(int j = 0; j < target[i].length; j++) {
                target[i][j] = addition[i][j] == null ? target[i][j] : addition[i][j];
            }
        }
    }

    public static <T> float[][][] marching_squares(T[][] grid, float pixel_size) {
        Set<T[][]> split = split(grid, grid);
        float[][][] result = new float[split.size()][][];
        int i = 0;
        for(T[][] target : split) {
            result[i] = marching_squares_single(target, pixel_size);
            i++;
        }
        return result;
    }

    public static <T> float[][] marching_squares_single(T[][] grid, float pixel_size) {
        float[][] result = new float[grid.length * grid[0].length * 4][2];
        int index = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    float x = i * pixel_size;
                    float y = j * pixel_size;
                    float x1 = x + pixel_size;
                    float y1 = y + pixel_size;
                    if(i > 0 && grid[i - 1][j] == null) {
                        result[index] = new float[]{x, y};
                        result[index + 1] = new float[]{x, y1};
                        index += 2;
                    }
                    if(i < grid.length - 1 && grid[i + 1][j] == null) {
                        result[index] = new float[]{x1, y};
                        result[index + 1] = new float[]{x1, y1};
                        index += 2;
                    }
                    if(j > 0 && grid[i][j - 1] == null) {
                        result[index] = new float[]{x, y};
                        result[index + 1] = new float[]{x1, y};
                        index += 2;
                    }
                    if(j < grid[i].length - 1 && grid[i][j + 1] == null) {
                        result[index] = new float[]{x, y1};
                        result[index + 1] = new float[]{x1, y1};
                        index += 2;
                    }
                }
            }
        }
        return Arrays.copyOf(result, index);
    }
}
