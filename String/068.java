public class Solution {
    static class Buffer {
        List<String> buff;
        int lineWidth;
        int wordCnt = 0;
        int acc;
        
        public Buffer(int lw) {
            buff = new ArrayList<>();
            lineWidth = lw;
            acc = 0;
        }
        
        private String nspace(int n) {
            StringBuilder sb = new StringBuilder();
            for(; n>0; n--) 
                sb.append(' ');
            return sb.toString();
        }

        public boolean offer(String next) {
            if (next.length() + acc + wordCnt > lineWidth) {
                return false;
            } else {
                buff.add(next);
                wordCnt++;
                acc += next.length();
                return true;
            }
        }
        
        public String popLast() {
            int rem = lineWidth - (acc + wordCnt - 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < buff.size()-1; i++) {
                sb.append(buff.get(i));
                sb.append(" ");
            }
            sb.append(buff.get(buff.size()-1));
            sb.append(nspace(rem));
            return sb.toString();
        }
        
        private void clear() {
            buff.clear();
            wordCnt = 0;
            acc = 0;
        }
        
        public String poll() {
            StringBuilder sb = new StringBuilder();
            if (wordCnt == 1) { 
                sb.append(buff.get(0));
                int rem = lineWidth - buff.get(0).length();
                sb.append(nspace(rem));
            } else{
                boolean flag = ((lineWidth - acc) % (wordCnt - 1) == 0);
                if (flag) {
                    int space = (lineWidth - acc) / (wordCnt - 1);
                    for (int i = 0; i < buff.size()-1; i++) {
                        sb.append(buff.get(i));
                        sb.append(nspace(space));
                    }
                    sb.append(buff.get(buff.size()-1));
                } else {
                    int space = (lineWidth - acc) / (wordCnt - 1);
                    int cnt = wordCnt - 1;
                    int rem = lineWidth - acc;
                    for (int i = 0; i < buff.size()-1; i++) {
                        sb.append(buff.get(i));
                        if (rem > cnt*space) {
                            sb.append(nspace(space+1));
                            rem -= space + 1;
                            cnt--;
                        } else {
                            sb.append(nspace(space));
                            rem -= (space);
                            cnt--;
                        }
                    }
                    sb.append(buff.get(buff.size()-1));
                }
            }
            clear();
            return sb.toString();
        }
    }
    
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        Buffer buf = new Buffer(maxWidth);
        for (int i = 0; i < words.length; i++) {
            if (buf.offer(words[i])) continue;
            else {
                res.add(buf.poll());
                i--;
            }
        }
        if (!buf.buff.isEmpty()) res.add(buf.popLast());
        return res;
    }
}