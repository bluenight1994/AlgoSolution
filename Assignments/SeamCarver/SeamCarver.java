package com.hong.algo;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by GanHong on 3/24/17.
 */
public class SeamCarver {

    private Picture pic;
    private double[][] energyMatrix;

    public SeamCarver(Picture picture) {
        // create a seam carver object based on the given picture
        pic = new Picture(picture);
        energyMatrix = new double[pic.height()][pic.width()];
        for (int i = 0; i < pic.height(); i++) {
            for (int j = 0; j < pic.width(); j++) {
                energyMatrix[i][j] = energy(j, i);
            }
        }
    }

    public Picture picture() {
        // current picture
        return pic;
    }

    public int width() {
        // width of current picture
        return pic.width();
    }

    public int height() {
        // height of current picture
        return pic.height();
    }

    public double energy(int x, int y) {
        // energy of pixel at column x and row y
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IndexOutOfBoundsException();
        int left_x = (x == 0) ? width() - 1 : x - 1;
        int right_x = (x == width() - 1) ? 0 : x + 1;
        int up_y = (y == 0) ? height() - 1: y - 1;
        int down_y = (y == height() - 1) ? 0 : y + 1;
        Color lx = pic.get(left_x, y);
        Color rx = pic.get(right_x, y);
        Color uy = pic.get(x, up_y);
        Color dy = pic.get(x, down_y);
        double detX = 0.0;
        double detY = 0.0;
        detX += (lx.getRed() - rx.getRed()) * (lx.getRed()-rx.getRed());
        detX += (lx.getBlue() - rx.getBlue()) * (lx.getBlue()-rx.getBlue());
        detX += (lx.getGreen() - rx.getGreen()) * (lx.getGreen() - rx.getGreen());
        detY += (uy.getRed() - dy.getRed()) * (uy.getRed() - dy.getRed());
        detY += (uy.getGreen() - dy.getGreen()) * (uy.getGreen() - dy.getGreen());
        detY += (uy.getBlue() - dy.getBlue()) * (uy.getBlue() - dy.getBlue());
        return Math.sqrt(detX + detY);
    }

    public int[] findHorizontalSeam() {
        // The goal is to find the shortest path from any of the W pixels
        // in the top row to any of the W pixels in the bottom row
        double[][] acc = new double[pic.height()][pic.width()];
        int[][] track = new int[pic.height()][pic.width()];
        for (int i = 0; i < pic.height(); i++) {
            acc[i][0] = energyMatrix[i][0];
            track[i][0] = -1;
        }
        for (int i = 1; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                double left, middle, right;
                left = (j == 0) ? Double.MAX_VALUE : acc[j-1][i-1];
                middle = acc[j][i-1];
                right = (j == pic.height() - 1) ? Double.MAX_VALUE : acc[j+1][i-1];
                acc[j][i] = Math.min(Math.min(left, middle), right) + energyMatrix[j][i];
                if (acc[j][i] == left + energyMatrix[j][i])
                    track[j][i] = j - 1;
                else if (acc[j][i] == middle + energyMatrix[j][i])
                    track[j][i] = j;
                else
                    track[j][i] = j + 1;
            }
        }
        int id = -1;
        double v = Double.MAX_VALUE;
        for (int i = 0; i < pic.height(); i++) {
            if (acc[i][pic.width()-1] < v) {
                v = acc[i][pic.width()-1];
                id = i;
            }
        }
        int[] ret = new int[pic.width()];
        int index = pic.width() - 1;
        ret[index--] = id;
        for (int i = pic.width() - 1; i > 0; i--) {
            ret[index--] = track[id][i];
            id = track[id][i];
        }
        return ret;
    }

    public int[] findVerticalSeam() {
        double[][] acc = new double[pic.height()][pic.width()];
        int[][] track = new int[pic.height()][pic.width()];
        for (int i = 0; i < pic.width(); i++) {
            acc[0][i] = energyMatrix[0][i];
            track[0][i] = -1;
        }
        for (int i = 1; i < pic.height(); i++) {
            for (int j = 0; j < pic.width(); j++) {
                double left, middle, right;
                left = (j == 0) ? Double.MAX_VALUE : acc[i-1][j-1];
                middle = acc[i-1][j];
                right = (j == pic.width() - 1) ? Double.MAX_VALUE : acc[i-1][j+1];
                acc[i][j] = Math.min(Math.min(left, middle), right) + energyMatrix[i][j];
                if (acc[i][j] == left + energyMatrix[i][j])
                    track[i][j] = j - 1;
                else if (acc[i][j] == middle + energyMatrix[i][j])
                    track[i][j] = j;
                else
                    track[i][j] = j + 1;
            }
        }
        int id = -1;
        double v = Double.MAX_VALUE;
        for (int i = 0; i < pic.width(); i++) {
            if (acc[pic.height()-1][i] < v) {
                v = acc[pic.height()-1][i];
                id = i;
            }
        }
        int[] ret = new int[pic.height()];
        int index = pic.height() - 1;
        ret[index--] = id;
        for (int i = pic.height() - 1; i > 0; i--) {
            ret[index--] = track[i][id];
            id = track[i][id];
        }
        return ret;
    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
        Picture tmp = new Picture(width(), height()-1);
        for (int i = 0; i < width(); i++) {
            int index = 0;
            for (int j = 0; j < height(); j++) {
                if (j != seam[i]) {
                    Color color = pic.get(i, j);
                    tmp.set(i, index++, color);
                }
            }
        }
        pic = tmp;
    }

    public void removeVerticalSeam(int[] seam) {
        Picture tmp = new Picture(width() - 1, height());
        for (int i = 0; i < height(); i++) {
            int index = 0;
            for (int j = 0; j < width(); j++) {
                if (j != seam[i]) {
                    Color color = pic.get(j, i);
                    tmp.set(index++, i, color);
                }
            }
        }
        pic = tmp;
    }

    public static void main(String[] args) {
        // unit test for findSeam:
        Picture pic1 = new Picture("HJocean.png");
        SeamCarver sc = new SeamCarver(pic1);
        for (int i = 0 ; i < 50; i++) {
            sc.removeHorizontalSeam(sc.findHorizontalSeam());
            sc.pic.show();
        }
    }
}
