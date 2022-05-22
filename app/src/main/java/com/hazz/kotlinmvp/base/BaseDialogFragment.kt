package com.hazz.kotlinmvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    fun show(manager: FragmentManager){
        manager.beginTransaction().remove(this).commit()
        super.show(manager,this.javaClass.name)
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }
}