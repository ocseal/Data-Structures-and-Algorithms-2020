public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDeque<Character>();
        d.addFirst(word.charAt(0));
        for (int i = 1; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        if (word.length() == 0) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        int i = 0;
        int j = word.length() - 1;
        while (i <= j) {
            if (d.get(i) != d.get(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word != null && cc == null) {
            return isPalindrome(word);
        }
        if (word == null) {
            return false;
        }
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (!cc.equalChars(d.get(i), d.get(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
