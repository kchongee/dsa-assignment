package entity;

import UtilityClasses.CMD;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.CircularQueue;
import adtImplementation.HashMap;

import static UtilityClasses.jdbcUtil.readAll;
import static UtilityClasses.jdbcUtil.readOne;

public class CommentQueue
{
    ArrayList<HashMap<String, Object>> commentsMap;
    CircularQueue<Comment> commentQueue;
    Room room;


    // constructor
    public CommentQueue(Room room)
    {
        this.room = room;
        commentQueue = new CircularQueue<Comment>(50);
        updateData();
    }


    // region 001 : getter setter
    public ArrayList<HashMap<String, Object>> getCommentsMap() {
        return commentsMap;
    }

    public void setCommentsMap(ArrayList<HashMap<String, Object>> commentsMap) {
        this.commentsMap = commentsMap;
    }

    public CircularQueue<Comment> getCommentQueue() {
        return commentQueue;
    }

    public void setCommentQueue(CircularQueue<Comment> commentQueue) {
        this.commentQueue = commentQueue;
    }
    // endregion


    // region 002 : public method
    public void updateData()
    {
        fillMap50Comment();

        // debug
        // System.out.println(commentsMap.toString());

        fillQueue50Comment();

        HashMap<String, Object> firstAcc = commentsMap.get(0);

        /*
        * Problem
        * Statement : account obj info concern + acctype
        * */

        Account acc = new Account((String)firstAcc.get("accountID"), (String)firstAcc.get("username"));
    }


    public boolean newCommentDetected()
    {
        Comment latestCommentDB = fetchLatestCommentFromDB();
        Comment lastFetchedLatest = trackLatestComment();
        return latestCommentDB.compareTo(lastFetchedLatest) != 0;
    }
    // endregion


    // region 003 : utility method
    private void fillQueue50Comment()
    {
        for (int i = commentsMap.size()-1 ; i >= 0 ; i--)
        {
            String userName = (String) commentsMap.get(i).get("username");
            String accountID = (String) commentsMap.get(i).get("accountID");
            int isSeller = (Integer) commentsMap.get(i).get("isSeller");

            Account account = new Account(accountID, userName,isSeller);
            Comment tempComment = new Comment(account, room, commentsMap.get(i));
             // debug
             // System.out.println(tempComment.toString());
            commentQueue.add(tempComment);
        }
    }


    private void fillMap50Comment()
    {
        String query =
                String.format
                (
                        """
                        select acc.accountID, userName, isSeller, commentTime, content, commentDate
                        from   comment c, account acc
                        where  acc.accountID = c.accountID and roomID = %s
                        ORDER BY commentSeq desc
                        LIMIT 50;
                        """,room.getRoomID()
                );

        // debug
        // System.out.println(query);
        ArrayList<HashMap<String, Object>> tempMap = readAll(query);

        if (tempMap != null) {
            commentsMap = readAll(query);
        }

        commentsMap = readAll(query);
        HashMap<String, Object> firstAcc = commentsMap.get(0);
    }


    private Comment trackLatestComment()
    {
        String userName = (String) commentsMap.get(0).get("username");
        String accountID = (String) commentsMap.get(0).get("accountID");
        int isSeller = (Integer) commentsMap.get(0).get("isSeller");
        Account account = new Account(accountID, userName,isSeller);
        return new Comment(account, room, commentsMap.get(0));
    }


    private Comment fetchLatestCommentFromDB()
    {
        String subquery = String.format
                (
                        "(SELECT max(commentSeq)\n" +
                        "FROM   Comment\n" +
                        "WHERE  roomID='%s')", room.getRoomID()
                );

        String query = String.format
                (
                        "SELECT acc.accountID, username, isSeller, commentDate, commentTime, roomID, content\n" +
                        "FROM Account acc, Comment comm\n" +
                        "WHERE acc.accountID = comm.accountID AND roomID='%s' AND commentSeq=%s;\n", room.getRoomID(), subquery
                );

        HashMap<String,Object> data = readOne(query);

        if (data!= null)
        {
            Account account = new Account((String)data.get("accountID"), (String)data.get("username"), (Integer) data.get("isSeller"));
            return new Comment(account, room, data);
        }
        else
        {
            return null;
        }
    }
    // endregion
}
