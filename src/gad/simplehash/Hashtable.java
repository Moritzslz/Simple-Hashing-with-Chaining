package gad.simplehash;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.*;
import java.util.stream.Stream;

public class Hashtable<K, V> {
    private List<Pair<K, V>>[] table;
    private int [] a;

    @SuppressWarnings("unchecked")
    public Hashtable(int minSize, int[] a) {
        table = new List[getNextPowerOfTwo(minSize)];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<>();
        }
        this.a = a;
    }

    public List<Pair<K, V>>[] getTable() {
        return table;
    }

    public static int getNextPowerOfTwo(int i) {
        if (i <= 1)
            return 1;
        int p = 2;
        while (i > p) {
            p *= 2;
        }
        return p;
    }

    public static int fastModulo(int i, int divisor) {
        return i & (divisor - 1);
    }

    private byte[] bytes(K k) {
        return k.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int h(K k, ModuloHelper mH) {
        byte[] x = bytes(k);
        int sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * a[i % a.length];
        }
        return mH.doTheMagic(sum, table.length);
    }

    public void insert(K k, V v, ModuloHelper mH) {
        int hash = h(k, mH);
        table[hash].add(new Pair<>(k, v));
    }

    public boolean remove(K k, ModuloHelper mH) {
        int hash = h(k, mH);
        if (table[hash].isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < table[hash].size(); i++) {
                if (table[hash].get(i).one().equals(k))
                    table[hash].remove(i);
            }
            return true;
        }
    }

    public Optional<V> find(K k, ModuloHelper mH) {
        int hash = h(k, mH);
        if (!table[hash].isEmpty()) {
            List<Pair<K, V>> entries = table[hash];
            for (Pair<K, V> entry : entries) {
                if (entry.one() == k)
                    return Optional.ofNullable(entry.two());
            }
        }
        return Optional.empty();
    }

    public List<V> findAll(K k, ModuloHelper mH) {
        List<V> values = new ArrayList<>();
        int hash = h(k, mH);
        if (!table[hash].isEmpty()) {
            List<Pair<K, V>> entries = table[hash];
            for (Pair<K, V> entry : entries) {
                if (entry.one() == k)
                    values.add(entry.two());
            }
        }
        return values;
    }

    public Stream<Pair<K, V>> stream() {
        return Stream.of(table).filter(Objects::nonNull).flatMap(List::stream);
    }

    public Stream<K> keys() {
        return stream().map(Pair::one).distinct();
    }

    public Stream<V> values() {
        return stream().map(Pair::two);
    }


    public static void main(String[] args) {
        System.out.println(getNextPowerOfTwo(3));
        System.out.println(getNextPowerOfTwo(-10));
        System.out.println(getNextPowerOfTwo(0));
        System.out.println(getNextPowerOfTwo(65));
        System.out.println(fastModulo(2, 2));
        System.out.println(fastModulo(11, 2));
        System.out.println(fastModulo(11, 8));
        System.out.println(fastModulo(65, 64));
        System.out.println(fastModulo(63, 64));
        System.out.println(fastModulo(73, 16));
    }
}