package com.example.ivcare.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.ivcare.R
import com.example.ivcare.database.UserDatabase
import com.example.ivcare.database.UsersRepository
import com.example.ivcare.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UsersRepository(dao)
        val factory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel


        loginViewModel.statusMessage.observe(viewLifecycleOwner, Observer{
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

        loginViewModel.navigateToUserDisplay.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = LoginFragmentDirections.actionLoginFragmentToPatientDisplayFragment()
                NavHostFragment.findNavController(this).navigate(action)
                loginViewModel.doneNavigatingToUserDisplay()
            }
        })

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                NavHostFragment.findNavController(this).navigate(action)
                loginViewModel.doneNavigatingToRegister()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            findNavController().popBackStack(R.id.myNavHostFragment, false)
//        }
//    }


}