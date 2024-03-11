package app.vegan;

import static app.vegan.R.drawable.error;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class govegandoc extends AppCompatActivity {

    private ImageView doc1, doc2, doc3,doc4, doc5, doc6, doc7, doc8, film, film2, film3, film4, beepoint, doc99, doc98, doc97, do1, do2, do3, vegadm;
    private  String whatDoc;
    private GifImageView gif;
    FirebaseFirestore fStore;
    String contador;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govegandoc);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton backButton = findViewById(R.id.imageBack);
        vegadm = findViewById(R.id.imageView512);
        do1 = findViewById(R.id.do1);
        do2 = findViewById(R.id.do2);
        do3 = findViewById(R.id.do3);
        doc1 = findViewById(R.id.doc1);
        doc2 = findViewById(R.id.doc2);
        doc3 = findViewById(R.id.doc3);
        doc4 = findViewById(R.id.doc4);
        doc5 = findViewById(R.id.doc5);
        doc6 = findViewById(R.id.doc6);
        doc7 = findViewById(R.id.doc7);
        doc8 = findViewById(R.id.doc8);
        film = findViewById(R.id.film);
        film2 = findViewById(R.id.film2);
        film3 = findViewById(R.id.film3);
        film4 = findViewById(R.id.film4);
        doc99 = findViewById(R.id.film99);
        doc98 = findViewById(R.id.fil98);
        doc97 = findViewById(R.id.film97);
        gif = findViewById(R.id.gif);
        beepoint = findViewById(R.id.imageView44);
        fStore = FirebaseFirestore.getInstance();


      createNotificationChannel();

        int Emoji = 0x1F49A;

        String stringEmoji = String.valueOf(Emoji);

        stringEmoji = (new String(Character.toChars(Emoji)));

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, govegandoc.class), PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "lemubitA")
                .setSmallIcon(R.drawable.veganosbrand)
                .setContentTitle((getResources().getString(R.string.parabens)) + stringEmoji )
                .setContentIntent(contentIntent)
                .setContentText((getResources().getString(R.string.guianoti)))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        startVibrate(100);

        int mNotificationId = 001;

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mNotificationId, builder.build());

        TextView video1 = findViewById(R.id.video1);

        video1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/U5hGQDLprA8"));
                startActivity(browserIntent);
            }
        });

        TextView video2 = findViewById(R.id.video2);


        video2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/7PR64HGJoyk"));
                startActivity(browserIntent);
            }
        });

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                gif.setVisibility(View.GONE);
                beepoint.setAnimation(AnimationUtils.loadAnimation(govegandoc.this, R.anim.zoomin));
            }
        }, 3000 );

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beepoint.clearAnimation();
                beepoint.setVisibility(View.GONE);
            }
        }, 6000 );


        do1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="-2";
                showDocDialog();
            }
        });

        do2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="-1";
                showDocDialog();
            }
        });

        do3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="0";
                showDocDialog();
            }
        });

        doc1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="1";
                showDocDialog();
            }
        });
        doc2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="2";
                showDocDialog();
            }
        });
        doc3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="5";
                showDocDialog();
            }
        });
        doc4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="4";
                showDocDialog();
            }
        });
        doc5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="7";
                showDocDialog();
            }
        });
        doc6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="11";
                showDocDialog();
            }
        });
        doc7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="8";
                showDocDialog();
            }
        });
        doc8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="10";
                showDocDialog();
            }
        });
        film.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="3";
                showDocDialog();
            }
        });
        film2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="6";
                showDocDialog();
            }
        });
        film3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="9";
                showDocDialog();
            }
        });
        film4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="12";
                showDocDialog();
            }
        });
        doc99.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="13";
                showDocDialog();
            }
        });
        doc98.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="14";
                showDocDialog();
            }
        });
        doc97.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                whatDoc="15";
                showDocDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent ( govegandoc.this, Goveganmore5.class );
                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                        R.anim.slide_in_right, R.anim.slide_out_right );
                ActivityCompat.startActivity ( govegandoc.this, intent1, actcompat.toBundle ( ) );
                finish();
            }
        });

        vegadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText key = new EditText(v.getContext());
                key.setHint("******");
                AlertDialog.Builder veglord = new AlertDialog.Builder(v.getContext(), R.style.MyAlertDialogTheme);
                veglord.setTitle("Entrada permitida apenas para funcionários");
                veglord.setMessage("Senha: ");
                veglord.setView(key);
                veglord.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = key.getText().toString();
                        DocumentReference docRef = fStore.collection("veglordkey").document("senha");
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String chaveBD = document.getString("key");

                                        if(Objects.equals(chaveBD, password)){
                                            //entrou
                                            Toast.makeText(govegandoc.this, R.string.insiradescdocup, Toast.LENGTH_SHORT).show();
                                            Intent boss = new Intent(govegandoc.this, ActivityOwner.class);
                                            startActivity(boss);
                                        }
                                        else {
                                            //fecha app
                                            finish();
                                            System.exit(0);
                                        }


                                    }
                                }
                            }
                        });


                    }
                });
                veglord.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                veglord.create().show();
            }
        });


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.aboutustxt);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showDocDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.docbig, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();
        ImageView docimage = view.findViewById(R.id.docimage);
        TextView legendyt = view.findViewById(R.id.legendyt);
        ImageView tap = view.findViewById(R.id.imageView98);
        ImageView tap2 = view.findViewById(R.id.imageVie9);
        tap2.setVisibility(View.VISIBLE);
        tap.setVisibility(View.VISIBLE);
        legendyt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        switch (whatDoc) {
            case "-2":
                try {
                    Picasso.get().load(R.drawable.veganoperi).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/kr98MSULN9g"));
                        startActivity(browserIntent);
                    }

                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/kr98MSULN9g"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "-1":
                try {
                    Picasso.get().load(R.drawable.orfaosdoleite).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/c3okNTmgKWE"));
                        startActivity(browserIntent);
                    }

                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/c3okNTmgKWE"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "0":
                try {
                    Picasso.get().load(R.drawable.onthewildside).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/KBcOeP3Y-sA"));
                        startActivity(browserIntent);
                    }

                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/KBcOeP3Y-sA"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "1":
                try {
                    Picasso.get().load(R.drawable.acarne).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/euvdedl-qso"));
                        startActivity(browserIntent);
                    }

                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/euvdedl-qso"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "2":
                try {
                    Picasso.get().load(R.drawable.orfaodoleite).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/3rziZBnZQrA"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/3rziZBnZQrA"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "3":
                try {
                    Picasso.get().load(R.drawable.carnage).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/wSreSNaLtZQ"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/wSreSNaLtZQ"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "4":
                try {
                    Picasso.get().load(R.drawable.whatthe).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/NoYINMgImGs"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/NoYINMgImGs"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "5":
                try {
                    Picasso.get().load(R.drawable.seaspira).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/1Q5CXN7soQg"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/1Q5CXN7soQg"));
                        startActivity(browserIntent);
                    }

                });
                break;
            case "6":
                try {
                    Picasso.get().load(R.drawable.earthlings).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/8gqwpfEcBjI"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/8gqwpfEcBjI"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "7":
                try {
                    Picasso.get().load(R.drawable.blackfish).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dTddaT7MjeY"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dTddaT7MjeY"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "8":
                try {
                    Picasso.get().load(R.drawable.killus12).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/BSrUnhxUazA"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/BSrUnhxUazA"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "9":
                try {
                    Picasso.get().load(R.drawable.killus).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/EQRJ8Pz0QX0"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/EQRJ8Pz0QX0"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "10":
                try {
                    Picasso.get().load(R.drawable.cowsshort).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/BqyiSnhV11I"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/BqyiSnhV11I"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "11":
                try {
                    Picasso.get().load(R.drawable.thegamechan).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/iSpglxHTJVM"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/iSpglxHTJVM"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "12":
                try {
                    Picasso.get().load(R.drawable.behindthemask).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/0dCNbuEBWMg"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/0dCNbuEBWMg"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "13":
                try {
                    Picasso.get().load(R.drawable.okja).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/GhyHd2gPLMU"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/GhyHd2gPLMU"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "14":
                try {
                    Picasso.get().load(R.drawable.boldnative).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaqui));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/Km_dBIBjtXg"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/Km_dBIBjtXg"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case "15":
                try {
                    Picasso.get().load(R.drawable.alf2012).placeholder(R.drawable.loading).into(docimage);
                } catch (Exception e) {
                    docimage.setImageDrawable(ContextCompat.getDrawable(govegandoc.this, R.drawable.error));
                }
                legendyt.setText(getResources().getString(R.string.cliqueaquip));
                legendyt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dIKlNOPNb88"));
                        startActivity(browserIntent);
                    }
                });
                docimage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dIKlNOPNb88"));
                        startActivity(browserIntent);
                    }
                });
                break;
        }
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

}
