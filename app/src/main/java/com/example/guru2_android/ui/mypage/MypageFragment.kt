package com.example.guru2_android.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.guru2_android.databinding.FragmentMydiaryBinding

class MypageFragment : Fragment() {

    private var _binding: FragmentMydiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(MypageViewModel::class.java)

        _binding = FragmentMydiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //텍스트뷰 바인딩인데, 텍스트뷰 안쓰고 빈칸이라 일단 주석처리
    //    val textView: TextView = binding.myDiaryTextView
    //    galleryViewModel.text.observe(viewLifecycleOwner) {
    //        textView.text = it
    //    }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}