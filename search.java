import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
//this class does the searching, it can do dfs, bfs, iterative deepening, and A* 
public class search 
{
	//set if debug will be printed or not
	public void set_debug(boolean b)
	{
		debug=b;
	}
	//set up the collection to be used in searching 
	public void init(int i, problem p)
	{
		search_kind=i;
		//bfs
		if(search_kind==0)
		{
			state_q=new LinkedBlockingQueue<problem.state>();
			state_q.offer(p.get_init_state());
		}
		//dfs
		else if(search_kind==1)
		{
			state_s=new Stack<problem.state>();
			state_s.push(p.get_init_state());
		}
		//a_star
		else if(search_kind==2)
		{
			state_p=new PriorityQueue<problem.state>();
			state_p.offer(p.get_init_state());
		}
		//iterative_deeppening
		else
		{
			state_s=new Stack<problem.state>();
			state_s.push(p.get_init_state());
			max_depth=2;
		}
	}
	
	public void bfs(problem p)
	{
		//grab initial state if it exists, which it should
		problem.state current_state;
		if(state_q.size()!=0)
		{
			current_state=state_q.poll();
		}
		else
		{
			System.out.println("queue is empty no solution in bfs");
			return;
		}
		
		//while current state is not an answer keep looking
		while(!p.test_goal(current_state))
		{
			//get successors
			ArrayList<problem.state> hold=p.make_successors(current_state);
			for(int i=0; i<hold.size(); i++)
			{
				//add to queue if it is not already in the branch 
				if(hold.get(i).test_dup())
				{
					state_q.offer(hold.get(i));
				}
			}
			//pop if able if not then no answer 
			if(state_q.size()!=0)
			{
				current_state=state_q.poll();
			}
			else
			{
				System.out.println("queue is empty so no solution in bfs ");
				return;
			}
		}
		
		System.out.println("solution found, depth is "+current_state.depth);
		print_ans(current_state,p);
		if(debug)
		{
			System.out.println("expanded is "+p.get_expanded());
		}
	}
	
	public void dfs(problem p)
	{
		problem.state current_state;
		if(state_s.size()!=0)
		{
			current_state=state_s.pop();
		}
		else
		{
			System.out.println("stack is empty no solution in dfs");
			return;
		}
		
		//while current state is not an answer keep looking
		//put a high depth limit, so it will not run forever, although it should have a depth of 9! at most, which
		//is less than 400000
		while(!p.test_goal(current_state) && current_state.depth<400000)
		{
			//print depth every 10000 times, just to tell that it is still running
			if(current_state.depth%10000==0)
			{
				System.out.println(current_state.depth);
			}
			//get successors
			ArrayList<problem.state> hold=p.make_successors(current_state);
			for(int i=0; i<hold.size(); i++)
			{
				//add to stack if it is not already in the branch 
				if(hold.get(i).test_dup())
				{
					state_s.push(hold.get(i));
				}
			}
			//pop if able if not then no answer 
			if(state_s.size()!=0)
			{
				current_state=state_s.pop();
			}
			else
			{
				System.out.println("stack is empty so no solution in dfs ");
				return;
			}
		}
		
		System.out.println("solution found, depth is "+current_state.depth);
		//only print path it if is not huge 
		if(current_state.depth<100)
		{
			print_ans(current_state,p);
		}
		if(debug)
		{
			System.out.println("expanded is "+p.get_expanded());
		}
	}
	
	public void a_star(problem p)
	{
		//grab initial state if it exists, which it should
		problem.state current_state;
		if(state_p.size()!=0)
		{
			current_state=state_p.poll();
		}
		else
		{
			System.out.println("queue is empty no solution in bfs");
			return;
		}
				
		//while current state is not an answer keep looking
		while(!p.test_goal(current_state))
		{
			//get successors
			ArrayList<problem.state> hold=p.make_successors(current_state);
			for(int i=0; i<hold.size(); i++)
			{
				//add to queue if it is not already in the branch 
				if(hold.get(i).test_dup())
				{
					//set the heuristic of this state
					hold.get(i).heuristic=p.calc_heuristic(hold.get(i));
					state_p.offer(hold.get(i));
				}
			}
			//pop if able if not then no answer 
			if(state_p.size()!=0)
			{
				current_state=state_p.poll();
			}
			else
			{
				System.out.println("priority_queue is empty so no solution in bfs ");
				return;
			}
		}
				
		System.out.println("solution found, depth is "+current_state.depth);
		if(current_state.depth<100)
		{
			print_ans(current_state,p);
		}
		if(debug)
		{
			System.out.println("expanded is "+p.get_expanded());
		}
	}
	
	public void iter_deep(problem p)
	{
		problem.state current_state;
		if(state_s.size()!=0)
		{
			current_state=state_s.pop();
		}
		else
		{
			System.out.println("stack is empty no solution in itt_deep");
			return;
		}
		
		//while current state is not an answer keep looking
		//put a high depth limit, so it will not run forever, although it should have a depth of 9! at most, which
		//is less than 400000
		while(!p.test_goal(current_state) && current_state.depth<400000)
		{
			//get successors
			ArrayList<problem.state> hold=p.make_successors(current_state);
			for(int i=0; i<hold.size(); i++)
			{
				//add to stack if it is not already in the branch 
				if(hold.get(i).test_dup() && hold.get(i).depth<=max_depth)
				{
					state_s.push(hold.get(i));
				}
			}
			//pop if able if not then go deeper
			if(state_s.size()!=0)
			{
				current_state=state_s.pop();
			}
			//go one deeper 
			else
			{
				max_depth++;
				state_s.push(p.get_init_state());
				current_state=state_s.pop();
				if(max_depth%1==0)
				{
					System.out.println("stack is empty increment to "+max_depth); 
				}
			}
		}
		
		System.out.println("solution found, depth is "+current_state.depth);
		//only print path it if is not huge 
		if(current_state.depth<100)
		{
			print_ans(current_state,p);
		}
		if(debug)
		{
			System.out.println("expanded is "+p.get_expanded());
		}
		
	}
	//takes a state and traces it to the initial state, called to get path to solution 
	public void print_ans(problem.state s, problem p)
	{
		while(s!=null)
		{
			p.print_state(s);
			s=s.parent;
		}
	}
	
	//says if debug info should be printed or not
	private boolean debug;
	//0 for bfs, 1 for dfs
	private int search_kind;
	//stack for dfs, and iterative deepening 
	private Stack<problem.state> state_s; 
	//queue for bfs 
	private LinkedBlockingQueue<problem.state> state_q;
	//priority queue for A*
	private PriorityQueue<problem.state> state_p;
	//depth that iterative deepening can go
	private int max_depth;
}
