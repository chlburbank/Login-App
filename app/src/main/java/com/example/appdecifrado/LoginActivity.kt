package com.example.appdecifrado

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.appdecifrado.AdminSQLiteOpenHelper;

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recogida de los variables de Views
        var userField: EditText = findViewById(R.id.usernameField);
        var passwordField: EditText = findViewById(R.id.passwordField);
        var notificationLabel: TextView = findViewById(R.id.notificationText);

        var btnSignIn: Button = findViewById(R.id.btnSignIn);
        var btnLogIn: Button = findViewById(R.id.btnLogIn);
        var btnModify: Button = findViewById(R.id.btnModify);
        var btnDelete: Button = findViewById(R.id.btnDelete);

        btnSignIn.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "userTable", null, 1);
            val db = admin.writableDatabase;
            val registry = ContentValues();
            registry.put("usuario", userField.text.toString());
            registry.put("contraseña", passwordField.text.toString());
            db.insert("usuarios", null, registry);
            db.close();
            userField.setText("");
            passwordField.setText("");
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
        }

        btnLogIn.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "userTable", null, 1);
            val db = admin.writableDatabase;
            val row = db.rawQuery("SELECT usuario, contraseña FROM usuarios WHERE usuario = ? AND contraseña = ?", arrayOf(userField.text.toString(), passwordField.text.toString()));
            if (row.moveToFirst()) {
                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent);
            } else {
                notificationLabel.setText("User does not Exist. Please try again.")
            }
            row.close();
            db.close();
        }

        btnModify.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "userTable", null, 1);
            val db = admin.writableDatabase;
            val registry = ContentValues();
            registry.put("contraseña", passwordField.text.toString());
            val mod = db.update("usuarios", registry, "usuario = ?", arrayOf(userField.text.toString()));
            db.close();
            if (mod == 1) {
                Toast.makeText(this,"The password has been modified", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
            }
        }

        btnDelete.setOnClickListener{
            val admin = AdminSQLiteOpenHelper(this, "userTable", null, 1);
            val db = admin.writableDatabase;
            val row = db.delete("usuarios", "usuario = ? AND contraseña = ?", arrayOf(userField.text.toString(), passwordField.text.toString()));
            db.close();
            userField.setText("");
            passwordField.setText("");
            if (row == 1) {
                Toast.makeText(this,"User Deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Could not delete specified user.", Toast.LENGTH_SHORT).show()
            }
        }


    }
}