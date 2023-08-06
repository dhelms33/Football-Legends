package edu.msudenver.cs3013.project03
//TODO: Delete unused imports and unused variables alongside unused code
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.msudenver.cs3013.project03.PASSWORD_KEY
import edu.msudenver.cs3013.project03.USER_NAME_KEY

//this activity is where all menu fragments are displayed and it receives the login information from main activity
class SecondActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second) //sets content view to activity_second.xml
        setSupportActionBar(findViewById(R.id.toolbar)) //sets toolbar to toolbar.xml
        intent.let { loginIntent -> //getting login information from main activity

            val userNameForm = loginIntent?.getStringExtra(USER_NAME_KEY) ?: ""//getting username
            val passwordForm = loginIntent?.getStringExtra(PASSWORD_KEY) ?: "" //getting password
            val helloMessage =
                "Hello, $userNameForm!"//creating hello message, this does not display even after troubleshooting
            showWelcomeDialog(userNameForm) //showing welcome dialog
        }
        val navHostFragment = //setting up navigation
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) //supportFragmentManager handles most of the work
                    as NavHostFragment
        val navController = navHostFragment.navController//manages navigation
        appBarConfiguration = AppBarConfiguration( //setting up nav bar or hamburger menu
            setOf(
                R.id.nav_home, R.id.nav_account, R.id.nav_favorites,
                R.id.nav_archive, R.id.nav_bin
            ),
            findViewById(R.id.drawer_layout) //find id of hamburger menu
        )
        setupActionBarWithNavController(navController, appBarConfiguration) //setting up action bar
        findViewById<NavigationView>(R.id.nav_view_drawer)?.setupWithNavController(navController) //setting up nav drawer
        findViewById<BottomNavigationView>(R.id.nav_view_bottom)?.setupWithNavController(
            navController
        ) //setting up bottom nav bar

    }

    private fun showWelcomeDialog(userName: String?) {
        val builder = AlertDialog.Builder(this)
        val message = if (userName.isNullOrEmpty()) {
            "Thank you for using Football Legends,\n we have updated the app, see the home menu for more information."
        } else {
            "Welcome, $userName!\nWe have updated Football Legends, see the home menu for more information."
        }
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu) //inflating menu, displays bottom nav bar
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //allows for navigation to other fragments
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) //returns selected item

    }


    override fun onSupportNavigateUp(): Boolean { //if true, navigates up
        val navController =
            findNavController(R.id.nav_host_fragment)//fine the desired navController then find appropriate fragment
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp() //return true if navigated up or recursively call self
    }
}
