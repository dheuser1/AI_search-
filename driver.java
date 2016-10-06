//This class takes the command line arguments and parses them
//It will initialize the problem and search objects
import java.lang.Math;
import java.util.ArrayList;

public class driver 
{
	public static final double LOG2=Math.log(2);
	//sample input
	//puz dfs on 5 1 4 8 7 2 6 0 3
	//hardest 8_puzzle 8 6 7 2 5 4 3 0 1
	//and 6 4 7 8 5 0 3 2 1 
	public static void main(String[] args) 
	{
		//problem_type, search_type, debug_on_off, if_puz->start_state, 
		//if->search_type==a_s then last arg is out for out-of-place and man for manhattan distance
		//note that a_s is only for 8_puz
		//puz_or_riv, bfs_or_dfs_or_a_s_or_itd, on_or_off
		
		//for wow_puz
		search my_search=new search();
		int temp[]=new int[25];
		for(int i=0; i<25; i++)
		{
			temp[i]=Integer.parseInt(args[i]);
		}
		wow_puz my_wow_puz = new wow_puz(temp);
		my_wow_puz.set_heuristic_kind(0);
		my_search.set_debug(true);
		//my_search.init(2, my_wow_puz);
		//my_search.a_star(my_wow_puz);
		
		//bfs uses too much ram branching factor of 25
		//my_search.init(0, my_wow_puz);
		//my_search.bfs(my_wow_puz);
		
		my_search.init(3, my_wow_puz);
		my_search.iter_deep(my_wow_puz);
		
		//end for wow_puz
		
		
		
		/* rest
		if(args[2].equals("on"))
		{
			my_search.set_debug(true);
		}
		else
		{
			my_search.set_debug(false);
		}
		
		if(args[0].equals("riv"))
		{
			if(args[1].equals("bfs"))
			{
				fwgc my_fwgc=new fwgc();
				my_search.init(0, my_fwgc);
				my_search.bfs(my_fwgc);
			}
			else if(args[1].equals("dfs"))
			{
				fwgc my_fwgc=new fwgc();
				my_search.init(1, my_fwgc);
				my_search.dfs(my_fwgc);
			}
			else
			{
				fwgc my_fwgc=new fwgc();
				my_search.init(3, my_fwgc);
				my_search.iter_deep(my_fwgc);
			}
		}
		else
		{
			int temp[]=new int[9];
			for(int i=0; i<9; i++)
			{
				temp[i]=Integer.parseInt(args[i+3]);
			}
			if(args[1].equals("bfs"))
			{
				puzzle_8 my_puz=new puzzle_8(temp);
				my_search.init(0, my_puz);
				my_search.bfs(my_puz);
			}
			else if(args[1].equals("dfs"))
			{
				puzzle_8 my_puz=new puzzle_8(temp);
				my_search.init(1, my_puz);
				my_search.dfs(my_puz);
			}
			else if(args[1].equals("a_s"))
			{
				puzzle_8 my_puz=new puzzle_8(temp);
				my_search.init(2, my_puz);
				if(args[12].equals("out"))
				{
					my_puz.set_heuristic_kind(0);
				}
				else
				{
					my_puz.set_heuristic_kind(1);
				}
				my_search.a_star(my_puz);
			}
			else
			{
				puzzle_8 my_puz=new puzzle_8(temp);
				my_search.init(3, my_puz);
				my_search.iter_deep(my_puz);
			}
		}
		*/
	}
}
