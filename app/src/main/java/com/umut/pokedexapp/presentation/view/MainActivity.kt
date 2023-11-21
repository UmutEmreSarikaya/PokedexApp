package com.umut.pokedexapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.umut.pokedexapp.R
import com.umut.pokedexapp.databinding.ActivityMainBinding
import com.umut.pokedexapp.util.viewGone
import com.umut.pokedexapp.util.viewVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupToolbar(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container_view).navigateUp(appBarConfiguration)
    }

    private fun setupToolbar(navController: NavController, appBarConfig: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    fun updateToolbarView(fragmentName:String){
        when(fragmentName){
            "PokemonListFragment" -> {
                binding.ivPokeball.viewVisible()
                binding.tvPokedex.viewVisible()
                binding.searchView.viewVisible()
            }

            "PokemonDetailFragment" -> {
                binding.ivPokeball.viewGone()
                binding.tvPokedex.viewGone()
                binding.searchView.viewGone()
            }
        }

    }
}