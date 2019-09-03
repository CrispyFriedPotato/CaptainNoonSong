package com.example.sookchat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MyGPS extends AppCompatActivity {

    private Button button1;
    private TextView txtResult;
    protected static int campus = 0;
    private static MyGPS instance = null;
    /* 액티비티 간에 참조 가능한 건물 변수
    1 진리관 2 순헌관 3 명신관 4 새힘관 5 행정관 6 학생회관
    7 르네상스플라자 8 프라임관 9 음악대학 10 미술대학 11 사회교육관 12 약학대학
    13 백주년기념관 14 중앙도서관 15 과학관 */
    private String building = "건물을 알 수 없습니다";
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();


            //건물 추천 코드 1켐퍼스
            if(longitude>126.963275 && longitude<126.964115){
                if(latitude>37.546212 &&latitude<37.546789){
                    building = "진리관";
                    campus = 1;
                }
            }

            if(longitude>126.964115 &&longitude<126.965534){
                if(latitude>37.545991 &&latitude<37.546789){
                    building = "순헌관";
                    campus = 2;
                }
            }

            if(longitude>126.963275 &&longitude<126.964115){
                if(latitude>37.545600 && latitude<37.546212){
                    building = "명신관";
                    campus = 3;
                }
            }

            if(longitude>126.963275 &&longitude<126.964115){
                if(latitude>37.545076 &&latitude<37.545600){
                    building = "새힘관";
                    campus = 4;
                }
            }

            if(longitude>126.964115 &&longitude<126.964802){
                if(latitude>37.545076 && latitude<37.545991){
                    building = "행정관";
                    campus = 5;
                }
            }

            if(longitude>126.964802 &&longitude<126.965534){
                if(latitude>37.545076 &&latitude<37.545991){
                    building = "학생회관";
                    campus = 6;
                }
            }



            //건물추천코드 2캠퍼스

            if(longitude>126.963576 &&longitude<126.964279){
                if(latitude>37.544500 &&latitude<37.545076){
                    building = "르네상스플라자";
                    campus = 7;
                }
            }

            if(longitude>126.964279 &&longitude<126.965078){
                if(latitude>37.544679 &&latitude<37.545076){
                    building = "프라임관";
                    campus = 8;
                }
            }

            if(longitude>126.963576 &&longitude<126.964279){
                if(latitude>37.543954 &&latitude<37.544500){
                    building = "음악대학";
                    campus = 9;
                }
            }

            if(longitude>126.964279 &&longitude<126.964507){
                if(latitude>37.543954 &&latitude<37.544679){
                    building = "음악대학";
                    campus = 9;
                }
            }

            if(longitude>126.964507 &&longitude<126.965078){
                if(latitude>37.543954 &&latitude<37.544679){
                    building = "미술대학";
                    campus = 10;
                }
            }

            if(longitude>126.963576 &&longitude<126.964279){
                if(latitude>37.543584 &&latitude<37.543954){
                    building = "사회교육관";
                    campus = 11;
                }
            }

            if(longitude>126.964279 &&longitude<126.964780){
                if(latitude>37.543584 &&latitude<37.543954){
                    building = "약학대학";
                    campus = 12;
                }
            }

            if(longitude>126.965078 &&longitude<126.965759){
                if(latitude>37.544354 &&latitude<37.545015){
                    building = "프라임관";
                    campus = 8;
                }
            }

            if(longitude>126.965078 &&longitude<126.965759){
                if(latitude>37.543584 &&latitude<37.544354){
                    building = "백주년기념관";
                    campus = 13;
                }
            }

            if(longitude>126.965759 &&longitude<126.966812){
                if(latitude>37.543584 &&latitude<37.544354){
                    building = "중앙도서관";
                    campus = 14;
                }
            }

            if(longitude>126.965759 &&longitude<126.966812){
                if(latitude>37.544354 &&latitude<37.545076){
                    building = "과학관";
                    campus = 15;
                }
            }



            txtResult.setText("위치정보 : " + provider + "\n" +
                    "위도 : " + longitude + "\n" +
                    "경도 : " + latitude + "\n" +
                    "고도  : " + altitude + "\n" +
                    "건물 : " + building + "\n"
            );



            //txtResult.setText(provider);

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };



    protected  MyGPS(){} //Exsists only to defeat instatiation.

    private static MyGPS getInstance(){
        if(instance == null){
            instance = new MyGPS();

        }

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gps);
        button1 = (Button)findViewById(R.id.button1);
        txtResult = (TextView)findViewById(R.id.txtResult);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( MyGPS.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    String provider = LocationManager.NETWORK_PROVIDER;

                    Location location = lm.getLastKnownLocation(provider);

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    //건물 추천 코드 1켐퍼스
                    if(longitude>126.963275 && longitude<126.964115){
                        if(latitude>37.546212 &&latitude<37.546789){
                            building = "진리관";
                            campus = 1;
                        }
                    }

                    if(longitude>126.964115 &&longitude<126.965534){
                        if(latitude>37.545991 &&latitude<37.546789){
                            building = "순헌관";
                            campus = 2;
                        }
                    }

                    if(longitude>126.963275 &&longitude<126.964115){
                        if(latitude>37.545600 && latitude<37.546212){
                            building = "명신관";
                            campus = 3;
                        }
                    }

                    if(longitude>126.963275 &&longitude<126.964115){
                        if(latitude>37.545076 &&latitude<37.545600){
                            building = "새힘관";
                            campus = 4;
                        }
                    }

                    if(longitude>126.964115 &&longitude<126.964802){
                        if(latitude>37.545076 && latitude<37.545991){
                            building = "행정관";
                            campus = 5;
                        }
                    }

                    if(longitude>126.964802 &&longitude<126.965534){
                        if(latitude>37.545076 &&latitude<37.545991){
                            building = "학생회관";
                            campus = 6;
                        }
                    }
                    //건물추천코드 2캠퍼스
                    if(longitude>126.963576 &&longitude<126.964279){
                        if(latitude>37.544500 &&latitude<37.545076){
                            building = "르네상스플라자";
                            campus = 7;
                        }
                    }

                    if(longitude>126.964279 &&longitude<126.965078){
                        if(latitude>37.544679 &&latitude<37.545076){
                            building = "프라임관";
                            campus = 8;
                        }
                    }

                    if(longitude>126.963576 &&longitude<126.964279){
                        if(latitude>37.543954 &&latitude<37.544500){
                            building = "음악대학";
                            campus = 9;
                        }
                    }

                    if(longitude>126.964279 &&longitude<126.964507){
                        if(latitude>37.543954 &&latitude<37.544679){
                            building = "음악대학";
                            campus = 9;
                        }
                    }

                    if(longitude>126.964507 &&longitude<126.965078){
                        if(latitude>37.543954 &&latitude<37.544679){
                            building = "미술대학";
                            campus = 10;
                        }
                    }

                    if(longitude>126.963576 &&longitude<126.964279){
                        if(latitude>37.543584 &&latitude<37.543954){
                            building = "사회교육관";
                            campus = 11;
                        }
                    }

                    if(longitude>126.964279 &&longitude<126.964780){
                        if(latitude>37.543584 &&latitude<37.543954){
                            building = "약학대학";
                            campus = 12;
                        }
                    }

                    if(longitude>126.965078 &&longitude<126.965759){
                        if(latitude>37.544354 &&latitude<37.545015){
                            building = "프라임관";
                            campus = 8;
                        }
                    }

                    if(longitude>126.965078 &&longitude<126.965759){
                        if(latitude>37.543584 &&latitude<37.544354){
                            building = "백주년기념관";
                            campus = 13;
                        }
                    }

                    if(longitude>126.965759 &&longitude<126.966812){
                        if(latitude>37.543584 &&latitude<37.544354){
                            building = "중앙도서관";
                            campus = 14;
                        }
                    }

                    if(longitude>126.965759 &&longitude<126.966812){
                        if(latitude>37.544354 &&latitude<37.545076){
                            building = "과학관";
                            campus = 15;
                        }
                    }



                    txtResult.setText("위치정보 : " + provider + "\n" +
                            "위도 : " + longitude + "\n" +
                            "경도 : " + latitude + "\n" +
                            "고도  : " + altitude + "\n" +
                            "건물 : " + building + "\n"
                    );



                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                }
            }
        });

    }

    public int getValue(){

        return campus;

    }


}