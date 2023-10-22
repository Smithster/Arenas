import com.smithster.gr8plugin.classes;

public class LobbyVote {

    private ArrayList<String> options = new ArrayList<String>();
    private HashMap<Player, Arena> votes = new HashMap<Player, Arena>();

    public LobbyVote(ArrayList<Arena> options){
        this.options = options;
    }

    public void vote(Player player, Arena arena){
        votes.put(player, vote);
    }

    public Arena getWinner(){
        HashMap<Arena, Integer> voteCount = new HashMap<Arena, Integer>();
        Integer winningScore = 0
        Arena winner;
        for (Arena arena: this.votes.values()){
            Integer score;
            if (voteCount.containsKey(arena)){
                score = voteCount.get(arena) + 1;
                
                voteCount.put(arena, score);
            } else {
                voteCount.put(arena, 1);
            }

            if (score > winningScore){
                winningScore = score;
                winner = arena;
            }
        }

        return winner;

    }
}