// class CYKAlgorithm {
//     private Map<String, List<String>> grammar;

//     public CYKAlgorithm(Map<String, List<String>> grammar) {
//         this.grammar = grammar;
//     }

//     public boolean run(String input) {
//         int n = input.length();
//         List<String>[][] dp = new ArrayList[n][n];

//         // Initialize DP table
//         for (int i = 0; i < n; i++) {
//             dp[i][i] = new ArrayList<>();
//             grammar.forEach((key, value) -> {
//                 if (key.length() == 1 && key.charAt(0) == input.charAt(i)) {
//                     dp[i][i].addAll(value);
//                 }
//             });
//         }

//         // Fill DP table
//         for (int length = 2; length <= n; length++) {
//             for (int i = 0; i <= n - length; i++) {
//                 int j = i + length - 1;
//                 dp[i][j] = new ArrayList<>();
//                 for (int k = i; k < j; k++) {
//                     for (String left : dp[i][k]) {
//                         for (String right : dp[k + 1][j]) {
//                             String key = left + right;
//                             if (grammar.containsKey(key)) {
//                                 dp[i][j].addAll(grammar.get(key));
//                             }
//                         }
//                     }
//                 }
//             }
//         }

//         return dp[0][n - 1].contains("S");  // Assuming 'S' is the start symbol
//     }
// }
