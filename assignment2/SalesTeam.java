import java.util.LinkedList;
import java.util.ListIterator;


public class SalesTeam {
    /** LinkedList of Sales Team */
    private LinkedList<String> team;

    /** Create a SalesTeam object with the following 6 Sales People */
    public SalesTeam() {
        // Create new LinkedList object
        this.team = new LinkedList<String>();
        // Add team of 6 sales people. 
        this.team.add("Join Snow");
        this.team.add("Hairy Prater");
        this.team.add("Tyrion Canister");
        this.team.add("Surelock Holmes");
        this.team.add("Serious Block");
        this.team.add("Arya Stork");
    }

    /**
     * Randomly select a sales person from the team
     * @return the name of the sales person.
     */
    public String selectTeamMember() {
        // Generate random team member number
        int salesPersonIndex = (int) (Math.random() * getTeamSize());
        // Return the sales person.
        return this.team.get(salesPersonIndex);
    }


    /**
     * Selects a specific team member based on their number on the team.
     * @param index Index of the sales person who is to be fetched.
     * @return The name of the sales person.
     * @throws IndexOutOfBoundsException if the position selected exceeds what is permitted by team size.
     */
    public String selectTeamMember(int index) throws IndexOutOfBoundsException {
        if (index >= team.size()) {
            throw new IndexOutOfBoundsException("There are only " + getTeamSize() + " people on the sales team.");
        }
        return team.get(index);
    }

    /**
     * Displays all members of the sales team.
     */
    public void display() {
        // Initialize return 
        String teamString = "";
        ListIterator<String> iterator = this.team.listIterator();
        while (iterator.hasNext()) {
            teamString += iterator.next();
            if (iterator.hasNext()) {
                teamString += ", ";
            }
        }
        // Display the team.
        System.out.println(teamString);
    }

    /**
     * Fetches the size of the team.
     * @return number of people on the sales team.
     */
    public int getTeamSize() {
        return this.team.size();
    }

}