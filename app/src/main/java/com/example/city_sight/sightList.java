package com.example.city_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yandex.mapkit.geometry.Point;

public class sightList extends AppCompatActivity{

    Point point1 = new Point(55.703866418347054, 37.52851789680896);
    Point point2 = new Point(55.7539725364951, 37.62060187837107);
    Point point3 = new Point(55.75209572815884, 37.617488668583746);
    Point point4 = new Point(55.76009725303142, 37.619088479698306);
    Point point5 = new Point(55.81976012990292, 37.61163041091612);
    Sight sight1 = new Sight("МГУ им М.В. Ломоносова", "Главное здание МГУ\n" +
            "Центральное здание университетского комплекса Московского государственного университета на Воробьёвых горах. Самая высокая из семи построенных сталинских высоток. Высота - 183,2 метра, этажность центрального корпуса - 32 этажа. Высота основания над уровнем моря - 194 м.\n" +
            "Жанр:сталинская архитектура\n" +
            "Дата постройки:1 сентября 1953 г.", point1);
    Sight sight2 = new Sight("Красная площадь", "Красная площадь— главная площадь Москвы, расположена между Московским Кремлём и Китай-городом. Вдоль западной стороны площади расположен Московский Кремль, вдоль восточной— Верхние торговые ряды и Средние торговые ряды. На Красной площади расположены Лобное место, памятник Минину и Пожарскому, Мавзолей Владимира Ленина, некрополь у Кремлёвской стены. В северной части площади находятся Исторический музей и Казанский собор, в южной— Покровский собор. ", point2);
    Sight sight3 = new Sight("Московский Кремль", "UМосковский Кремль\n" +
            "Крепость в центре Москвы и древнейшая её часть, главный общественно-политический и историко-художественный комплекс города, официальная резиденция Президента Российской Федерации, вплоть до распада СССР в декабре 1991 года была официальной резиденцией Генерального секретаря ЦК КПСС. Одно из самых известных архитектурных сооружений в мире.\n" +
            "Дата постройки:1495 г.", point3);
    Sight sight4 = new Sight("Большой театр", "Здание Большого театра\n" +
            "Здание в центре Москвы, в котором располагается Государственный академический Большой театр.\n" +
            "Дата постройки:1821 г.\n", point4);
    Sight sight5 = new Sight("Останкинская телебашня", "Останкинская телебашня\n" +
            "Телевизионная и радиовещательная башня, расположенная в Останкинском районе Москвы. Высота здания - 540,1 м. По состоянию на весну 2022 года телебашня - 15-е по высоте сооружение из когда-либо существовавших, а также высочайшее сооружение в Европе и в России. Является членом-учредителем Международной федерации великих башен.\n" +
            "Высота:540 м\n" +
            "Дата постройки:1967 г.", point5);
    String[] titleList = {
            sight1.getTitle(), sight2.getTitle(), sight3.getTitle(), sight4.getTitle(), sight5.getTitle()
    };
    Sight[] sights = {
            sight1, sight2, sight3, sight4, sight5
    };

    User user = new User();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_list);

        Bundle arguments = getIntent().getExtras();
        user.setName(arguments.getString("name"));
        user.setSurname(arguments.getString("surname"));
        user.setEmail(arguments.getString("email"));

        textView = (TextView) findViewById(R.id.helloUser);
        textView.setText("Здравствуйте, ".concat(user.getName()).concat("!"));

        ListView sightList = (ListView) findViewById(R.id.sightList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titleList);
        sightList.setAdapter(adapter);

        sightList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mapAct(sights[position]);
            }
        });
    }

    public void mapAct(Sight sight) {
        Intent intent = new Intent(this, sightMap.class);
        intent.putExtra("title", sight.getTitle());
        intent.putExtra("fullDisc", sight.getFullDisc());
        intent.putExtra("workHours", sight.getWorkHours());
        intent.putExtra("latitude", sight.getCoordinates().getLatitude());
        intent.putExtra("longitude", sight.getCoordinates().getLongitude());
        startActivity(intent);
    }
}