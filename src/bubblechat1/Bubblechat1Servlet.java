package bubblechat1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.*;

import com.google.gson.Gson;


import de.svenjacobs.loremipsum.LoremIpsum;

@SuppressWarnings("serial")
public class Bubblechat1Servlet extends HttpServlet {
	private LoremIpsum ipsum;
	private static Random random;
	private static final float CHANCE_OF_IMAGE=0.05f;
	private static final int TOTAL_IMAGE=10;
	private static final int MAX_IMAGE_PER_MESSAGE=5;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		

		
		random = new Random();
		ipsum = new LoremIpsum();
		ArrayList<Message> msgs=new ArrayList<Message>();
		
		for (int i = 0; i < 100; i++) {
			boolean left = getRandomInteger(0, 1) == 0 ? true : false;
			int word = getRandomInteger(1, 10);
			int start = getRandomInteger(1, 40);
			String words = ipsum.getWords(word, start);
			Message msg=new Message();
			msg.left=left;
			msg.text=words;
			
			float f=random.nextFloat();
			if(f<CHANCE_OF_IMAGE){
				int n=getRandomInteger(1,MAX_IMAGE_PER_MESSAGE);
				for(int j=0;j<n;j++){
					int k=getRandomInteger(1,TOTAL_IMAGE);
					if(!msg.pictures.containsKey(k)){
						msg.pictures.put(k, "/res/thumb"+(k-1)+".png");
					}
				}
			}
			
			msgs.add(msg);
		}

		Gson gson = new Gson();
		
		
		resp.getWriter().print(gson.toJson(msgs));
	}
	
	private static int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
}
