public class IntArrayMax {
    public static int max(int[] m) {
        int i = 0;
        int max = 0;
        while (i < (m.length)){
            if (m[i] > max){
                max = m[i];
            }
            i += 1;
        }
        return max;
    }
    public static int max_for(int[] m) {
        int max = 0;
        for (int i = 0; i < m.length; i += 1){
            if (m[i] > max){
                max = m[i];
            }
        }
        return max;
    }


    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6}; 
       System.out.println(max_for(numbers));     
    }
}