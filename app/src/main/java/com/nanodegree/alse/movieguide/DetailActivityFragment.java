package com.nanodegree.alse.movieguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public static final String POSITION = "EXTRA_POSITION";
    final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private final String IMAGE_BASEURL = "http://image.tmdb.org/t/p/w185/";
    public Movie movie;

    public DetailActivityFragment() {
    }

 /*   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  View root = inflater.inflate(R.layout.fragment_detail1, container, false);
        View root = inflater.inflate(R.layout.detail_card, container, false);
   //  if(savedInstanceState==null) {
          //  movie = DetailFragment.movie;
             movie = FragmentDetail.movie;
         //  Log.v("DetailActivityFreagment",String.valueOf(movie.movieId));
            if (movie.movieId == -1) {
                return null;
            }
            String title = null;
            String overview = null;
            String releaseDate = null;
            double rating = 0.0;
            String imageURL = null;
            int id = 0;
            String noPosterUrl = "https://assets.tmdb.org/assets/f996aa2014d2ffddfda8463c479898a3/images/no-poster-w185.jpg";
      /*      ImageView toolImage = (ImageView) getActivity().findViewById(R.id.image_src).findViewById(R.id.imageViewplaces);
            String selection = Utility.getSelectionValue(getActivity());
            if (selection.equals(getString(R.string.pref_sort_favorite))) {
                if (movie.posterUrl != null && !movie.posterUrl.equals("null")) {
                    File file = new File(movie.posterUrl);
                    Picasso.with(getActivity()).load(file).
                            into(toolImage);
                }
            } else if (movie.posterUrl != null && !movie.posterUrl.equals("null")) {
                imageURL = IMAGE_BASEURL + movie.posterUrl;
                Picasso.with(getActivity()).load(imageURL).
                        into(toolImage);
            } else {
                Picasso.with(getActivity()).load(noPosterUrl).into(toolImage);
            }*/
        ImageView imageView = (ImageView)root.findViewById(R.id.detail_poster);
        String selection = Utility.getSelectionValue(getActivity());
        if (selection.equals(getString(R.string.pref_sort_favorite))) {
            Log.v("InsideDetail",selection);
            if (movie.posterUrl != null && !movie.posterUrl.equals("null")) {
                Log.v("InsideDetail2",selection);
                File file = new File(movie.posterUrl);
                Picasso.with(getActivity()).load(file).
                        into(imageView);
            }
        }
        else if (movie.posterUrl != null && !movie.posterUrl.equals("null")){
            imageURL = IMAGE_BASEURL + movie.posterUrl;
            Picasso.with(getActivity()).load(imageURL).
                    into(imageView);
        }
        else {
            Picasso.with(getActivity()).load(noPosterUrl).into(imageView);
        }

            TextView desc = (TextView) root.findViewById(R.id.detail_overview);
            desc.setText(movie.overview);


            TextView titleText = (TextView) root.findViewById(R.id.detail_title);
            titleText.setText(movie.title);

            TextView release = (TextView) root.findViewById(R.id.detail_release_date);
            release.setText(movie.date);
               // Log.v("DetailActivityFragment", "iii"+movie.movieId);

            RatingBar rate = (RatingBar) root.findViewById(R.id.detail_ratingBar);
            //change rating in terms of 5
            rate.setRating((float) ((movie.rating * 5) / 10));
      //  }
            return root;
    }

  /*  @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }*/
}
