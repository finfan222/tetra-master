package com.finfan.server;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public final class Rand {

    public double nextDouble(double n) {
        return ThreadLocalRandom.current().nextDouble(n);
    }

    public double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public double get(double n) {
        return nextDouble(n);
    }

    public int nextInt(int n) {
        return ThreadLocalRandom.current().nextInt(n);
    }

    public int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public int get(int n) {
        return nextInt(n);
    }

    public int get(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max == Integer.MAX_VALUE ? max : max + 1);
    }

    public long nextLong(long n) {
        return ThreadLocalRandom.current().nextLong(n);
    }

    public long nextLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public long get(long n) {
        return nextLong(n);
    }

    public long get(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max == Long.MAX_VALUE ? max : max + 1L);
    }

    public boolean calcChance(double applicableUnits, double totalUnits) {
        return applicableUnits > nextDouble(totalUnits);
    }

    public double nextGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    public boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public byte[] nextBytes(int count) {
        return nextBytes(new byte[count]);
    }

    public byte[] nextBytes(byte[] array) {
        ThreadLocalRandom.current().nextBytes(array);
        return array;
    }

    /**
     * Returns a randomly selected element taken from the given list.
     *
     * @param <T>  type of list elements.
     * @param list a list.
     * @return a randomly selected element.
     */
    public <T> T get(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(get(list.size()));
    }

    /**
     * Returns a randomly selected element taken from the given array.
     *
     * @param array an array.
     * @return a randomly selected element.
     */
    public int get(int[] array) {
        return array[get(array.length)];
    }

    /**
     * Returns a randomly selected element taken from the given array.
     *
     * @param <T>   type of array elements.
     * @param array an array.
     * @return a randomly selected element.
     */
    public <T> T get(T[] array) {
        return array[get(array.length)];
    }
}