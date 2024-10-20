package kh.edu.rupp.ite.mad_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.mad_project.R

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val textView = view.findViewById<TextView>(R.id.favorite_textView)
        textView.text = "Favorite Screen"
        return view
    }
}
