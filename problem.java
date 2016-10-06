import java.util.ArrayList;

public interface problem 
{
	//set up initial state 
	public void init();
	//see if current node is an answer
	public boolean test_goal(state s);
	//make child nodes from current node
	public ArrayList<state> make_successors(state s);
	//calculate the heuristic and then return it
	public int calc_heuristic(state s);
	public int get_expanded();
	//prints the state given 
	public void print_state(state s);
	//return the initial problem state
	public state get_init_state();
	
	//this is the sate class, a problem class knows how to interpret it 
	public class state implements Comparable<state>
	{
		//copy constructor 
		state(state s)
		{
			data=s.data.clone();
			parent=s;
			cost=s.cost+1;
			depth=s.depth+1;
			heuristic=0;
		}
		//constructor that takes initial data 
		state(int[] temp)
		{
			data=temp.clone();
			cost=0;
			depth=0;
			heuristic=0;
		}
		
		//see if two states are equal, they are if they have the same data
		public boolean equals(state s)
		{
			//data must be the same length to be equal 
			if(data.length!=s.data.length)
			{
				return false;
			}
			for(int i=0; i<s.data.length; i++)
			{
				if(data[i]!=s.data[i])
				{
					return false;
				}
			}
			return true;
		}
		//done so priority queue will work, see which state has better heuristic  
		public int compareTo(state s) 
		{
			if(heuristic<s.heuristic)
			{
				return -1;
			}
			else if(heuristic==s.heuristic)
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}
		
		//make sure state is not already in branch
		public boolean test_dup()
		{
			//checked initial state, as nothing else can be in branch it is okay
			if(parent==null)
			{
				return true;
			}
			//see if this state is already in the branch 
			state ancestor=parent;
			while(ancestor.parent!=null)
			{
				if(ancestor.equals(this))
				{
					return false;
				}
				ancestor=ancestor.parent;
			}
			return true;
		}
		//the problem sate
		public int[] data;
		//the parent that generated this state
		public state parent; 
		//cost to go from parent to child, is always 1 in first lab
		public int cost;
		//depth of state
		public int depth;
		//the heuristic value of the state
		public int heuristic;
	}	
}
