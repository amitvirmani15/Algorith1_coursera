public class WeightedBag implements UnionBag {

    private int[] components; int[] size ;int[] highest;

    public int getCount() {
        return count;
    }

    private int count;
    public WeightedBag(int[] n){
        components = new int[n.length];
        highest = new int[n.length];
        size = new int[n.length];
        for (int i =0; i <n.length; i++) {
            components[i] = n[i];
        }
        count = n.length;
    }
    @Override
    public void Union(int person1, int person2) {
        int p1 = Root(person1);
        int p2 = Root(person2);
        if(size[p1] < size[p2]) {
            components[p1] = p2 ;
            size[p2] = size[p2] +1;
            if (p1 < p2)
            {
                highest[p1] =p2;
                highest[p2] =p2;
            }
            else {
                highest[p2] =p1;
                highest[p1] =p1;
            }

        }
        else {
            components[p2] = p1 ;
            size[p1] = size[p1] +1;
            if (p1 < p2)
            {
                highest[p1] =p2;
                highest[p2] =p2;
            }
            else {
                highest[p2] =p1;
                highest[p1] =p1;
            }
        }
    }

    private int Root(int i) {
        while (i != components[i]) {
            i = components[i];
        }

        return i;
    }

}