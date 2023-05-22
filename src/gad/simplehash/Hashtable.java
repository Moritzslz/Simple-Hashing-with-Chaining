package gad.simplehash;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Hashtable<K, V> {
    private List<Pair<K, V>>[] table;
    private int [] a;

    @SuppressWarnings("unchecked")
    public Hashtable(int minSize, int[] a) {
        table = (List<Pair<K, V>>[]) new List[getNextPowerOfTwo(minSize)];
        this.a = a;
    }

    public List<Pair<K, V>>[] getTable() {
        return table;
    }

    public static int getNextPowerOfTwo(int i) {
        int p = 2;
        while (i > p) {
            p *= 2;
        }
        return p;
    }

    public static int fastModulo(int i, int divisor) {
        if (i < divisor) {
            if (i < 0)
                return 0;
            return i;
        } else if (i > divisor) {
            return i - ((i / divisor) * divisor);
            /*
            String binaryI = Integer.toBinaryString(i);
            if (binaryI.charAt(binaryI.length()-1) == 0) {
                //Case even
                int mod = i - ((i / divisor) * divisor);
                return mod;
            } else {
                //Case odd
                int mod = i - ((i / divisor) * divisor);
                return mod;
            }
             */
        }
        else
            return 0;
    }

    private byte[] bytes(K k) {
        return k.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int h(K k, ModuloHelper mH) {
        byte[] x = bytes(k);
        return 0;
    }

    public void insert(K k, V v, ModuloHelper mH) {
    }

    public boolean remove(K k, ModuloHelper mH) {
        return false;
    }

    public Optional<V> find(K k, ModuloHelper mH) {
        return Optional.empty();
    }

    public List<V> findAll(K k, ModuloHelper mH) {
        return null;
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
        System.out.println(getNextPowerOfTwo(65));
        System.out.println(fastModulo(2,2));
        System.out.println(fastModulo(11,2));
        System.out.println(fastModulo(11,8));
        System.out.println(fastModulo(65,64));
        System.out.println(fastModulo(63,64));
        System.out.println(fastModulo(73,16));
    }
}