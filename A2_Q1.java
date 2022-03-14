import java.util.*;

public class A2_Q1 {

    public static int[] game(String[][] board){
        String[][] deepCopyBoard;
        List<ArrayList<Integer>> neighborsList=new ArrayList<>();
        List<ArrayList<Integer>> result = new ArrayList<>();
        List<ArrayList<Integer>> ballsList = new ArrayList<>();
        List<ArrayList<Integer>> possibleWays = new ArrayList<>();
        deepCopyBoard = new String[board.length][board[0].length];
        List<ArrayList<Integer>> initialList = new ArrayList<>();
        int ballsRemain =0;
        int movesPerformed=0;

        for (int row1 = 0; row1 < board.length; row1++) {
            for (int column1 = 0; column1 < board[row1].length; column1++) {
                deepCopyBoard[row1][column1] = board[row1][column1];
            }
        }

        for (int row1 = 0; row1 < board.length; row1++) {
            for (int column1 = 0; column1 < board[row1].length; column1++) {
                if (Objects.equals(board[row1][column1], "o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row1);
                    rowColList.add(column1);
                    initialList.add(rowColList);
                    ballsList.add(rowColList);
                    ballsRemain+=1;
                }
            }
        }
        //System.out.println(ballsRemain);
        int count =0;
        possibleWays1(count, board, ballsList, deepCopyBoard, neighborsList, result, initialList, ballsRemain,movesPerformed ,possibleWays);


        HashMap<Integer, ArrayList<Integer>> resultMap = new HashMap<>();
        for (ArrayList<Integer> eachResult: result){
            if (resultMap.containsKey(eachResult.get(0))){
                resultMap.get(eachResult.get(0)).add(eachResult.get(1));
            }
            else {
                ArrayList<Integer> resultList = new ArrayList<>();
                resultList.add(eachResult.get(1));
                resultMap.put(eachResult.get(0), resultList);
            }
        }
        //System.out.println(Arrays.asList(resultMap));
        int[] result2 = new int[2];
        TreeMap<Integer, ArrayList<Integer>> sorted = new TreeMap<>(resultMap);
        int count2 = 0;
        for (Integer key: sorted.keySet()){
            if (count2 ==0){
                result2[0] = key;
                Collections.sort(sorted.get(key));
                result2[1] = sorted.get(key).get(0);
                count2 +=1;

            }
        }
        System.out.println(Arrays.toString(result2));

        return result2;

    }
    //run game
    static void possibleWays1(int count, String[][] board,  List<ArrayList<Integer>> ballsList1, String[][] deepCopyBoard,  List<ArrayList<Integer>> neighborList, List<ArrayList<Integer>> result, List<ArrayList<Integer>> initialList, int ballsRemain, int movesPerformed,List<ArrayList<Integer>> possible){
        //System.out.println("ballsList " + ballsList1);
       // printBoard(board);
        //sublist 1
        for (ArrayList<Integer> rowCol : ballsList1) {
            int row = rowCol.get(0);
            int column = rowCol.get(1);
            if (checkBall(board, row, column, neighborList)) {
                if (!possible.contains(rowCol)) {
                    possible.add(rowCol);
                }
            }

        }
        //System.out.println("possible" + possible);
        if (possible.isEmpty()){
            int ballss = ballsRemain;
            int movess = movesPerformed;
            ArrayList<Integer> resultList = new ArrayList<>();
            resultList.add(ballss);
            resultList.add(movess);
            result.add(resultList);
        }
        for (ArrayList<Integer> possible1 : possible) {
            //System.out.println("Parent: " + possible1);

            int row = possible1.get(0);
            int column = possible1.get(1);
            //check if the ball has neighbor
            neighborList.clear();
            List<ArrayList<Integer>> ballsList2 = new ArrayList<>();
            for (int i = 0; i < initialList.size(); i++) {
                if (i == count) {
                    ballsList2.add(0, initialList.get(i));
                } else {
                    ballsList2.add(initialList.get(i));
                }
            }
            //System.out.println("ballsList " + ballsList2);
            count +=1;
            board = new String[deepCopyBoard.length][deepCopyBoard[0].length];
            for (int row1 = 0; row1 < deepCopyBoard.length; row1++) {
                for (int column1 = 0; column1 < deepCopyBoard[row1].length; column1++) {
                    board[row1][column1] = deepCopyBoard[row1][column1];
                }
            }
            if (checkBall(board, row, column, neighborList)) {
                //System.out.println(neighborList);
                if (neighborList.size()>1){
                    for (ArrayList<Integer> neighbor: neighborList) {
                        board = new String[deepCopyBoard.length][deepCopyBoard[0].length];
                        for (int row1 = 0; row1 < deepCopyBoard.length; row1++) {
                            for (int column1 = 0; column1 < deepCopyBoard[row1].length; column1++) {
                                board[row1][column1] = deepCopyBoard[row1][column1];
                            }
                        }
                        List<ArrayList<Integer>> ballsList3 = new ArrayList<>();
                        for (int i = 0; i < initialList.size(); i++) {
                            if (i == count) {
                                ballsList3.add(0, initialList.get(i));
                            } else {
                                ballsList3.add(initialList.get(i));
                            }
                        }
                        ArrayList<Integer> ballsm = playGame(board, row, column, ballsList3, neighborList, ballsRemain, movesPerformed, neighbor);
                        //printBoard(board);
                        int balls = ballsm.get(0);
                        int moves = ballsm.get(1);
                        ArrayList<Integer> resultList = new ArrayList<>();
                        resultList.add(balls);
                        resultList.add(moves);
                        result.add(resultList);
                        //System.out.println("result " + result);
                        //printBoard(board);
                        //System.out.println(ballsList3);

                        //sublist2
                        List<ArrayList<Integer>> possibleList2=new ArrayList<>();
                        //printBoard(board);
                        List<ArrayList<Integer>> newNeighbor=new ArrayList<>();


                        for (ArrayList<Integer> rowCol : ballsList3) {
                            int row1 = rowCol.get(0);
                            int column1 = rowCol.get(1);
                            if (checkBall(board, row1, column1, newNeighbor)) {
                                possibleList2.add(rowCol);
                            }
                        }
                        //ballslist of the parent
                        List<ArrayList<Integer>> ballsList4 = new ArrayList<>();
                        for (int i = 0; i < ballsList3.size(); i++) {
                            ballsList4.add(ballsList3.get(i));
                        }
                        //board of the parent
                        String[][] board1 = new String[board.length][board[0].length];
                        for (int row1 = 0; row1 < board.length; row1++) {
                            for (int column1 = 0; column1 < board[row1].length; column1++) {
                                board1[row1][column1] = board[row1][column1];
                            }
                        }
                        HashMap<ArrayList<Integer>, String[][]> boardMap = new HashMap<>();
                        HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> ballsMap = new HashMap<>();
                        HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> childrenMap = new HashMap<>();
                        int ballie = result.get(result.size()-1).get(0);
                        int movie = result.get(result.size()-1).get(1);


                        for (ArrayList<Integer> possibleWays: possibleList2){

                            //System.out.println(possibleWays+" of"+ possible1);
                            List<ArrayList<Integer>> ballsList5 = new ArrayList<>();
                            for (int i = 0; i < ballsList4.size(); i++) {
                                ballsList5.add(ballsList4.get(i));
                            }
                            board = new String[board1.length][board1[0].length];
                            for (int row1 = 0; row1 < board1.length; row1++) {
                                for (int column1 = 0; column1 < board1[row1].length; column1++) {
                                    board[row1][column1] = board1[row1][column1];
                                }
                            }

                            List<ArrayList<Integer>> possibleList3=new ArrayList<>();
                            possibleWays2(board, ballsList5, newNeighbor, result, boardMap, childrenMap, ballsMap, initialList, ballie, movie, possibleList2, possibleWays, possibleList3);
                        }
                    }

                }
                else{
                    ArrayList<Integer> ballsm = playGame(board, row, column, ballsList2, neighborList, ballsRemain, movesPerformed, neighborList.get(0));
                    //printBoard(board);
                    int balls = ballsm.get(0);
                    int moves = ballsm.get(1);
                    ArrayList<Integer> resultList = new ArrayList<>();
                    resultList.add(balls);
                    resultList.add(moves);
                    result.add(resultList);
                    //System.out.println("result " + result);
                    neighborList.clear();
                    //sublist2
                    List<ArrayList<Integer>> possibleList2=new ArrayList<>();
                    //printBoard(board);

                    for (ArrayList<Integer> rowCol : ballsList2) {
                        int row1 = rowCol.get(0);
                        int column1 = rowCol.get(1);
                        if (checkBall(board, row1, column1, neighborList)) {
                            possibleList2.add(rowCol);
                        }
                    }
                    //System.out.println(ballsList2);
                    //System.out.println(possibleList2);
                    //System.out.println("Children of" + possible1 + ":   "+ possibleList2);
                    //ballslist of the parent
                    List<ArrayList<Integer>> ballsList3 = new ArrayList<>();
                    for (int i = 0; i < ballsList2.size(); i++) {
                        ballsList3.add(ballsList2.get(i));
                    }
                    //board of the parent
                    String[][] board1 = new String[board.length][board[0].length];
                    for (int row1 = 0; row1 < board.length; row1++) {
                        for (int column1 = 0; column1 < board[row1].length; column1++) {
                            board1[row1][column1] = board[row1][column1];
                        }
                    }
                    HashMap<ArrayList<Integer>, String[][]> boardMap = new HashMap<>();
                    HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> ballsMap = new HashMap<>();
                    HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> childrenMap = new HashMap<>();
                    int ballie = result.get(result.size()-1).get(0);
                    int movie = result.get(result.size()-1).get(1);


                    for (ArrayList<Integer> possibleWays: possibleList2){

                        //System.out.println(possibleWays+" of"+ possible1);
                        List<ArrayList<Integer>> ballsList4 = new ArrayList<>();
                        for (int i = 0; i < ballsList3.size(); i++) {
                            ballsList4.add(ballsList3.get(i));
                        }
                        board = new String[board1.length][board1[0].length];
                        for (int row1 = 0; row1 < board1.length; row1++) {
                            for (int column1 = 0; column1 < board1[row1].length; column1++) {
                                board[row1][column1] = board1[row1][column1];
                            }
                        }

                        List<ArrayList<Integer>> possibleList3=new ArrayList<>();
                        possibleWays2(board, ballsList4, neighborList, result, boardMap, childrenMap, ballsMap, initialList, ballie, movie, possibleList2, possibleWays, possibleList3);
                    }
                }


            }
        }
    }
    static void possibleWays2(String[][] board, List<ArrayList<Integer>> ballsList1, List<ArrayList<Integer>> neighborList, List<ArrayList<Integer>> result, HashMap<ArrayList<Integer>, String[][]> boardMap, HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> childrenMap, HashMap<ArrayList<Integer>, List<ArrayList<Integer>>> ballsMap,List<ArrayList<Integer>> initialList, int ballsRemain, int movesPerformed,List<ArrayList<Integer>> possibleList, ArrayList<Integer> possible1, List<ArrayList<Integer>> possibleList3){
        int row = possible1.get(0);
        int column = possible1.get(1);
        neighborList.clear();
        //System.out.println("Children " + possible1);
        //printBoard(board);
        //
        if (checkBall(board, row, column, neighborList)) {
            //board of subset 1
            boardMap.put(possible1, board);

            if (neighborList.size()>1) {
                for (ArrayList<Integer> neighbor : neighborList) {
                    //keep the board
                    String[][] board1 = new String[board.length][board[0].length];
                    for (int row1 = 0; row1 < board.length; row1++) {
                        for (int column1 = 0; column1 < board[row1].length; column1++) {
                            board1[row1][column1] = board[row1][column1];
                        }
                    }
                    //keep the list
                    List<ArrayList<Integer>> ballsList3 = new ArrayList<>();
                    for (int i = 0; i < ballsList1.size(); i++) {
                        ballsList3.add(ballsList1.get(i));
                    }
                    //run each neighbor
                    ArrayList<Integer> ballsm = playGame(board1, row, column, ballsList3, neighborList, ballsRemain, movesPerformed, neighbor);

                    //System.out.println("Board after "+possible1+ " skips " +neighbor);
                    //put neighbor's board into boardmap
                    boardMap.put(neighbor, board1);
                    //printBoard(board1);

                    //result
                    int balls = ballsm.get(0);
                    int moves = ballsm.get(1);
                    ArrayList<Integer> resultList = new ArrayList<>();
                    resultList.add(balls);
                    resultList.add(moves);
                    result.add(resultList);
                    //System.out.println("result " + result);
                    //System.out.println(ballsList3);

                    //check for next children
                    List<ArrayList<Integer>> newNeighbor = new ArrayList<>();
                    possibleList3.clear();
                    for (ArrayList<Integer> rowCol : ballsList3){
                        int row2 = rowCol.get(0);
                        int column2 = rowCol.get(1);
                        if (checkBall(board1, row2, column2, newNeighbor)) {
                            possibleList3.add(rowCol);
                        }

                    }

                    childrenMap.put(neighbor, possibleList3);
                    ballsMap.put(neighbor, ballsList3);

                    int balls2 = result.get(result.size()-1).get(0);
                    int moves2 = result.get(result.size()-1).get(1);
                    for (ArrayList<Integer> possibleWays: possibleList3){

                        board1 = new String[boardMap.get(neighbor).length][boardMap.get(neighbor)[0].length];
                        for (int row1 = 0; row1 < boardMap.get(neighbor).length; row1++) {
                            for (int column1 = 0; column1 < boardMap.get(neighbor)[row1].length; column1++) {
                                board1[row1][column1] = boardMap.get(neighbor)[row1][column1];
                            }
                        }
                        List<ArrayList<Integer>> ballsList4 = new ArrayList<>();
                        for (int i = 0; i < ballsMap.get(neighbor).size(); i++) {
                            ballsList4.add(ballsMap.get(neighbor).get(i));
                        }

                        List<ArrayList<Integer>> possibleList9=new ArrayList<>();
                        possibleWays2(board1, ballsList4, newNeighbor, result, boardMap, childrenMap, ballsMap, initialList, balls2, moves2, possibleList, possibleWays, possibleList9);
                        //System.out.println("After "+possibleWays);
                        //printBoard(board1);
                    }
                }
            }
            else{
                ArrayList<Integer> ballsm = playGame(board, row, column, ballsList1, neighborList, ballsRemain, movesPerformed, neighborList.get(0));
                int balls = ballsm.get(0);
                int moves = ballsm.get(1);
                ArrayList<Integer> resultList = new ArrayList<>();
                resultList.add(balls);
                resultList.add(moves);
                result.add(resultList);
                //possibleList3.clear();
                //System.out.println("After "+possible1);
                //printBoard(board);
                //System.out.println(ballsList1);
                for (ArrayList<Integer> rowCol : ballsList1){
                    int row2 = rowCol.get(0);
                    int column2 = rowCol.get(1);
                    neighborList.clear();
                    if (checkBall(board, row2, column2, neighborList)) {
                        possibleList3.add(rowCol);
                    }

                }
                //System.out.println(possibleList3);
                childrenMap.put(possible1, possibleList3);
                ballsMap.put(possible1, ballsList1);
                //System.out.println(boardMap);
                //System.out.println(childrenMap);

                int balls2 = result.get(result.size()-1).get(0);
                int moves2 = result.get(result.size()-1).get(1);
                for (ArrayList<Integer> possibleWays: possibleList3){
                    //save the board [2,6]
                    //System.out.println(possibleWays);
                    board = new String[boardMap.get(possible1).length][boardMap.get(possible1)[0].length];
                    for (int row1 = 0; row1 < boardMap.get(possible1).length; row1++) {
                        for (int column1 = 0; column1 < boardMap.get(possible1)[row1].length; column1++) {
                            board[row1][column1] = boardMap.get(possible1)[row1][column1];
                        }
                    }
                    List<ArrayList<Integer>> ballsList4 = new ArrayList<>();
                    for (int i = 0; i < ballsMap.get(possible1).size(); i++) {
                        ballsList4.add(ballsMap.get(possible1).get(i));
                    }

                    List<ArrayList<Integer>> possibleList9=new ArrayList<>();
                    possibleWays2(board, ballsList4, neighborList, result, boardMap, childrenMap, ballsMap, initialList, balls2, moves2, possibleList, possibleWays, possibleList9);

                }

            }

            //printBoard(board);


        }

    }

    //check if there's a ball next to it
    static boolean checkBall(String[][] board, int row, int column,  List<ArrayList<Integer>> neighborsList){
        if (row==0) {
            int count = 0;
            String left = board[row][column - 1];
            String right = board[row][column + 1];
            String down = board[row + 1][column];
            if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                ArrayList<Integer> rowColList = new ArrayList<>();
                rowColList.add(row);
                rowColList.add(column - 1);
                count +=1;
                neighborsList.add(rowColList);
            }
            if (right.equals("o") && !board[row][column + 2].equals("#") && !board[row][column + 2].equals("o")) {
                ArrayList<Integer> rowColList2 = new ArrayList<>();
                rowColList2.add(row);
                rowColList2.add(column + 1);
                count +=1;
                neighborsList.add(rowColList2);
            }
            if (down.equals("o") && !board[row + 2][column].equals("#") && !board[row + 2][column].equals("o")) {
                ArrayList<Integer> rowColList3 = new ArrayList<>();
                rowColList3.add(row + 1);
                rowColList3.add(column);
                count +=1;
                neighborsList.add(rowColList3);
            }
            return count>0;

        }

        else if (row == board.length-1) {
            int count = 0;
            String left = board[row][column - 1];
            String right = board[row][column + 1];
            if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                ArrayList<Integer> rowColList = new ArrayList<>();
                rowColList.add(row);
                rowColList.add(column - 1);
                count +=1;
                neighborsList.add(rowColList);
            }
            if (right.equals("o")&& !board[row][column + 2].equals("#")&& !board[row][column + 2].equals("o")) {
                ArrayList<Integer> rowColList2 = new ArrayList<>();
                rowColList2.add(row);
                rowColList2.add(column + 1);
                neighborsList.add(rowColList2);
                count +=1;
                String up = board[row - 1][column];
            }
            String up = board[row - 1][column];
            if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                ArrayList<Integer> rowColList3 = new ArrayList<>();
                rowColList3.add(row - 1);
                rowColList3.add(column);
                count +=1;
                neighborsList.add(rowColList3);

            }
            return count>0;



        }

        else if (row == 1){
            if (column == 0 || column ==1){
                int count = 0;
                String right = board[row][column + 1];
                String down = board[row + 1][column];
                if (right.equals("o")&& !board[row][column + 2].equals("#")&& !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    count +=1;
                    neighborsList.add(rowColList);
                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    count +=1;
                    neighborsList.add(rowColList);
                }
                return count>0;
            }
            else if (column ==7 || column ==8){
                int count = 0;
                String left = board[row][column - 1];
                String down = board[row + 1][column];
                if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count +=1;

                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count +=1;
                }
                return count>0;
            }
            else{
                String right = board[row][column + 1];
                String left = board[row][column - 1];
                String down = board[row + 1][column];
                int count = 0;
                if (right.equals("o")&& !board[row][column + 2].equals("#")&& !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    neighborsList.add(rowColList);
                    count +=1;
                }
                if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count +=1;
                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count +=1;
                }
                return count>0;

            }
        }
        else if (row ==2){
            if (column == 0|| column ==1){
                int count = 0;
                String right = board[row][column + 1];
                String up = board[row - 1][column];
                String down = board[row + 1][column];
                if (right.equals("o") && !board[row][column + 2].equals("#") && !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;

            }
            else if (column == 7 || column == 8) {
                int count = 0;
                String up = board[row - 1][column];
                String down = board[row + 1][column];
                String left = board[row][column - 1];
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;
            }
            else{
                String left = board[row][column - 1];
                String right = board[row][column + 1];
                String up = board[row - 1][column];
                String down = board[row + 1][column];
                int count = 0;
                if (right.equals("o")&& !board[row][column + 2].equals("#")&& !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if(down.equals("o") && !board[row+2][column].equals("#")&& !board[row+2][column].equals("o")){
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row+1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;

            }
        }
        else if (row == 3) {
            if (column == 0 || column == 1) {
                int count = 0;
                String right = board[row][column + 1];
                String up = board[row - 1][column];
                if (right.equals("o") && !board[row][column + 2].equals("#") && !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;
            } else if (column == 7 || column == 8) {
                String left = board[row][column - 1];
                String up = board[row - 1][column];
                int count = 0;
                if (left.equals("o") && !board[row][column - 2].equals("#") && !board[row][column - 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;
            }
            else{
                String up = board[row - 1][column];
                String left = board[row][column - 1];
                String right = board[row][column + 1];
                int count = 0;
                if (right.equals("o")&& !board[row][column + 2].equals("#")&& !board[row][column + 2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column + 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (left.equals("o")&& !board[row][column -2].equals("#")&& !board[row][column -2].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row);
                    rowColList.add(column - 1);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                if (up.equals("o")&& !board[row-2][column].equals("#")&& !board[row-2][column].equals("o")) {
                    ArrayList<Integer> rowColList = new ArrayList<>();
                    rowColList.add(row - 1);
                    rowColList.add(column);
                    neighborsList.add(rowColList);
                    count+=1;
                }
                return count>0;

            }
        }
        return false;
    }
    //moves the ball
    static ArrayList<Integer> playGame(String[][] board, int row, int column, List<ArrayList<Integer>> ballsList1,  List<ArrayList<Integer>> neighborsList, int ballsRemain, int movesPerformed, ArrayList<Integer> neighbor) {
        int balls = ballsRemain;
        int moves = movesPerformed;

        //System.out.println("NeighborList" +neighborsList);

        ArrayList<Integer> rowColList = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        rowColList.add(row);
        rowColList.add(column);
        int nRow = neighbor.get(0);
        int nCol = neighbor.get(1);
        if (nCol<column) {
            if (board[row][column - 2].equals(".")) {
                board[row][column] = ".";
                board[row][column - 1] = ".";
                board[row][column - 2] = "o";
                moves += 1;
                balls -= 1;
                result.add(balls);
                result.add(moves);
                ArrayList<Integer> newRowColList = new ArrayList<>();
                newRowColList.add(row);
                newRowColList.add(column - 2);
                ballsList1.add(newRowColList);
                ballsList1.remove(rowColList);
                ballsList1.remove(neighbor);

            }
        }else if (nCol>column){
            if (board[row][column + 2].equals(".")) {
                board[row][column] = ".";
                board[row][column + 1] = ".";
                board[row][column + 2] = "o";
                moves += 1;
                balls -= 1;
                result.add(balls);
                result.add(moves);
                ArrayList<Integer> newRowColList = new ArrayList<>();
                newRowColList.add(row);
                newRowColList.add(column + 2);
                ballsList1.add(newRowColList);
                ballsList1.remove(rowColList);
                ballsList1.remove(neighbor);
            }
        }
        else if (nRow>row){
            if (board[row + 2][column].equals(".")) {
                board[row][column] = ".";
                board[row + 1][column] = ".";
                board[row + 2][column] = "o";
                moves += 1;
                balls -= 1;
                result.add(balls);
                result.add(moves);
                ArrayList<Integer> newRowColList = new ArrayList<>();
                newRowColList.add(row+2);
                newRowColList.add(column);
                ballsList1.add(newRowColList);
                ballsList1.remove(rowColList);
                ballsList1.remove(neighbor);
            }
        }
        else{
            if (board[row - 2][column].equals(".")) {
                board[row][column] = ".";
                board[row - 1][column] = ".";
                board[row - 2][column] = "o";
                moves += 1;
                balls -= 1;
                result.add(balls);
                result.add(moves);
                ArrayList<Integer> newRowColList = new ArrayList<>();
                newRowColList.add(row-2);
                newRowColList.add(column);
                ballsList1.add(newRowColList);
                ballsList1.remove(rowColList);
                ballsList1.remove(neighbor);
            }

        }

        return result;

    }
    static void printBoard(String[][] board){
        System.out.println(Arrays.toString(board[0]).replace(",", " "));
        System.out.println(Arrays.toString(board[1]).replace(",", " "));
        System.out.println(Arrays.toString(board[2]).replace(",", " "));
        System.out.println(Arrays.toString(board[3]).replace(",", " "));
        System.out.println(Arrays.toString(board[4]).replace(",", " "));
    }

/*
    public static void main(String args[]){
        String[][] board1 = {{"#", "#", "#", ".", "o", ".", "#", "#", "#"}, {".", ".", ".", "o", "o", ".", "o", ".", "."}, {".", ".", "o", ".", ".", ".", "o", ".", "."}, {"o", ".", ".", "o", ".", ".", ".", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board0 = {{"#", "#", "#", ".", "o", ".", "#", "#", "#"}, {".", ".", "o", ".", ".", ".", ".", "o", "."}, {".", ".", ".", ".", ".", "o", ".", ".", "."}, {".", ".", ".", ".", ".", ".", "o", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board2 = {{"#", "#", "#", ".", ".", ".", "#", "#", "#"}, {"o", ".", "o", ".", "o", ".", ".", ".", "o"}, {".", ".", "o", ".", ".", ".", "o", ".", "."}, {".", ".", ".", ".", ".", ".", "o", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board3 = {{"#", "#", "#", ".", ".", ".", "#", "#", "#"}, {"o", ".", "o", ".", ".", ".", ".", ".", "."}, {".", ".", ".", "o", ".", ".", ".", ".", "."}, {".", ".", "o", ".", ".", "o", ".", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board4= {{"#", "#", "#", ".", ".", ".", "#", "#", "#"}, {"o", "o", ".", ".", ".", ".", ".", ".", "."}, {".", "o", ".", "o", ".", "o", "o", ".", "."}, {".", ".", ".", ".", ".", ".", ".", ".", "."}, {"#", "#", "#", ".", ".", "o", "#", "#", "#"}};
        String[][] board5 = {{"#","#","#",".",".",".","#","#","#"}, {".",".","o","o",".",".",".",".","."}, {".",".",".",".",".","o","o",".","."}, {".",".",".",".",".",".",".",".","."}, {"#","#","#",".",".",".","#","#","#"}};
        String[][] board5 = {{"#", "#", "#", ".", ".", ".", "#", "#", "#"}, {".", ".", ".", ".", ".", ".", ".", "o", "."}, {".", ".", ".", ".", ".", ".", ".", ".", "."}, {".", ".", ".", ".", ".", ".", ".", "o", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board6 = {{"#", "#", "#", "o", "o", ".", "#", "#", "#"}, {"o", ".", ".", ".", ".", ".", ".", ".", "."}, {".", ".", ".", ".", "o", ".", ".", ".", "o"}, {".", "o", "o", "o", ".", ".", ".", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] boardTest = {{"#", "#", "#", ".", ".", ".", "#", "#", "#"}, {".", ".", "o", "o", ".", ".", ".", ".", "."}, {".", ".", ".", ".", ".", "o", "o", ".", "."}, {".", ".", ".", ".", ".", ".", ".", ".", "."}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        String[][] board7 = {{"#", "#", "#", "o", ".", ".", "#", "#", "#"}, {".", ".", ".", ".", ".", ".", ".", ".", "o"}, {".", "o", ".", ".", ".", ".", ".", ".", "."}, {"o", "o", "o", ".", ".", "o", ".", ".", "o"}, {"#", "#", "#", ".", ".", ".", "#", "#", "#"}};
        A2_Q1.game(board7);

    }*/


}
