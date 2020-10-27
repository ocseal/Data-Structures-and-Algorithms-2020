public class DrawTriangle{
    public static void main (String[] args){
        int row = 0;
        String prev_row = "";
        while (row < 5){
            drawrow(prev_row);
            prev_row = prev_row + "*";
            row += 1;
        }
    }
    public static void drawrow (String prev_row){
        System.out.println(prev_row+"*"); 
    }
}