package org.knoesis.health;

import java.util.List;

import org.knoesis.health.adapters.CommentsAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.CommentsDataHolder;

import android.os.Bundle;
import android.widget.ListView;


public class CommentsDisplay extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments_display);
		//Retrieving the comments of specific user
		List<CommentsDataHolder> comments=db.getComments(Constants.commentsId,asthmaApp.userLoggedIn);
		ListView commentsList=(ListView)findViewById(R.id.commentsList);
		if(comments != null){
			commentsList.setAdapter(new CommentsAdapter(comments, this));
		}
		
	}

}
