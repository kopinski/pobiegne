package pl.pobiegne.mobile.db;

import java.sql.SQLException;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.common.api.db.WorkoutType;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    /**
     * Nazwa bazy danych.
     */
    private static final String DATABASE_NAME = "pobiegne.db";
    
    /**
     * Wersja bazy danych.
     */
    private static final int DATABASE_VERSION = 1;
    
    /**
     * Dao dla skladnikow.
     */
    private Dao<Route, Integer> routetDao = null;
    
    
    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        final String methodName = "onCreate";
        try {
            Log.i(TAG, methodName);
            long millis = System.currentTimeMillis();
            TableUtils.createTable(connectionSource, Route.class);
            
            routetDao = getRouteDao();
            
            Route route = new Route();
            route.setName("Trasa z dnia 11-05-2013");
            route.setCalories(256);
            route.setDate(new DateTime(2013,05,11,0,0));
            route.setTotalTime(1450482);
            route.setDistance(7525.54);
            route.setWorkoutPointsCount(135);
            route.setWorkoutTime(1204584);
            route.setActivity(WorkoutType.CYCLING);
            route.setXml("<gpx><trk><name>Trasa z dnia 23-05-2013 19:22</name><trkseg><trkpt lat=\"51.75051315\" lon=\"19.43910104\"><time>2013-05-23T19:22:59.272+02:00</time><ele>236.10000610351563</ele></trkpt><trkpt lat=\"51.75054538\" lon=\"19.43908697\"><time>2013-05-23T19:23:00.230+02:00</time><ele>234.6999969482422</ele></trkpt><trkpt lat=\"51.75058075\" lon=\"19.43906565\"><time>2013-05-23T19:23:01.228+02:00</time><ele>236.1999969482422</ele></trkpt><trkpt lat=\"51.75062535\" lon=\"19.43907302\"><time>2013-05-23T19:23:02.274+02:00</time><ele>236.0</ele></trkpt><trkpt lat=\"51.75066417\" lon=\"19.43906346\"><time>2013-05-23T19:23:03.282+02:00</time><ele>235.89999389648438</ele></trkpt><trkpt lat=\"51.75070525\" lon=\"19.43903024\"><time>2013-05-23T19:23:04.264+02:00</time><ele>235.0</ele></trkpt><trkpt lat=\"51.75076167\" lon=\"19.43901806\"><time>2013-05-23T19:23:05.262+02:00</time><ele>234.3000030517578</ele></trkpt><trkpt lat=\"51.75080724\" lon=\"19.43900413\"><time>2013-05-23T19:23:06.268+02:00</time><ele>234.3000030517578</ele></trkpt><trkpt lat=\"51.75084118\" lon=\"19.43899757\"><time>2013-05-23T19:23:07.278+02:00</time><ele>233.89999389648438</ele></trkpt><trkpt lat=\"51.75085688\" lon=\"19.4389688\"><time>2013-05-23T19:23:08.280+02:00</time><ele>233.60000610351563</ele></trkpt><trkpt lat=\"51.75085417\" lon=\"19.43893262\"><time>2013-05-23T19:23:09.277+02:00</time><ele>234.10000610351563</ele></trkpt><trkpt lat=\"51.75087559\" lon=\"19.43894224\"><time>2013-05-23T19:23:10.264+02:00</time><ele>233.89999389648438</ele></trkpt><trkpt lat=\"51.7508945\" lon=\"19.43894328\"><time>2013-05-23T19:23:11.274+02:00</time><ele>233.6999969482422</ele></trkpt><trkpt lat=\"51.75088954\" lon=\"19.43894547\"><time>2013-05-23T19:23:12.257+02:00</time><ele>234.1999969482422</ele></trkpt><trkpt lat=\"51.75092955\" lon=\"19.43899043\"><time>2013-05-23T19:23:13.276+02:00</time><ele>234.0</ele></trkpt><trkpt lat=\"51.75092802\" lon=\"19.43900762\"><time>2013-05-23T19:23:14.265+02:00</time><ele>234.39999389648438</ele></trkpt><trkpt lat=\"51.75094287\" lon=\"19.43901547\"><time>2013-05-23T19:23:15.274+02:00</time><ele>234.60000610351563</ele></trkpt><trkpt lat=\"51.75096356\" lon=\"19.43904711\"><time>2013-05-23T19:23:16.291+02:00</time><ele>236.60000610351563</ele></trkpt><trkpt lat=\"51.75097658\" lon=\"19.43912049\"><time>2013-05-23T19:23:17.271+02:00</time><ele>235.5</ele></trkpt><trkpt lat=\"51.75098933\" lon=\"19.43918537\"><time>2013-05-23T19:23:18.268+02:00</time><ele>233.3000030517578</ele></trkpt><trkpt lat=\"51.7509847\" lon=\"19.43927371\"><time>2013-05-23T19:23:19.265+02:00</time><ele>234.1999969482422</ele></trkpt><trkpt lat=\"51.75096798\" lon=\"19.43932828\"><time>2013-05-23T19:23:20.256+02:00</time><ele>236.39999389648438</ele></trkpt><trkpt lat=\"51.75094313\" lon=\"19.43944691\"><time>2013-05-23T19:23:21.256+02:00</time><ele>235.3000030517578</ele></trkpt><trkpt lat=\"51.75094533\" lon=\"19.43953799\"><time>2013-05-23T19:23:22.294+02:00</time><ele>236.89999389648438</ele></trkpt><trkpt lat=\"51.75094857\" lon=\"19.43961625\"><time>2013-05-23T19:23:23.283+02:00</time><ele>237.0</ele></trkpt><trkpt lat=\"51.75096421\" lon=\"19.43974428\"><time>2013-05-23T19:23:24.270+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75097172\" lon=\"19.43984079\"><time>2013-05-23T19:23:25.274+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75098495\" lon=\"19.43995925\"><time>2013-05-23T19:23:26.283+02:00</time><ele>236.5</ele></trkpt><trkpt lat=\"51.75103828\" lon=\"19.44007696\"><time>2013-05-23T19:23:27.258+02:00</time><ele>237.6999969482422</ele></trkpt><trkpt lat=\"51.75105189\" lon=\"19.44018808\"><time>2013-05-23T19:23:28.275+02:00</time><ele>237.8000030517578</ele></trkpt><trkpt lat=\"51.75107176\" lon=\"19.44033744\"><time>2013-05-23T19:23:29.255+02:00</time><ele>237.39999389648438</ele></trkpt><trkpt lat=\"51.75109051\" lon=\"19.440476\"><time>2013-05-23T19:23:30.249+02:00</time><ele>236.5</ele></trkpt><trkpt lat=\"51.75109295\" lon=\"19.44063339\"><time>2013-05-23T19:23:31.277+02:00</time><ele>235.89999389648438</ele></trkpt><trkpt lat=\"51.75109466\" lon=\"19.44077818\"><time>2013-05-23T19:23:32.280+02:00</time><ele>235.10000610351563</ele></trkpt><trkpt lat=\"51.75110659\" lon=\"19.44090693\"><time>2013-05-23T19:23:33.284+02:00</time><ele>235.10000610351563</ele></trkpt><trkpt lat=\"51.7511445\" lon=\"19.4410456\"><time>2013-05-23T19:23:34.272+02:00</time><ele>234.89999389648438</ele></trkpt><trkpt lat=\"51.75114897\" lon=\"19.44114628\"><time>2013-05-23T19:23:35.329+02:00</time><ele>236.10000610351563</ele></trkpt><trkpt lat=\"51.75114487\" lon=\"19.44121912\"><time>2013-05-23T19:23:36.279+02:00</time><ele>235.8000030517578</ele></trkpt><trkpt lat=\"51.75115177\" lon=\"19.44132591\"><time>2013-05-23T19:23:37.281+02:00</time><ele>236.0</ele></trkpt><trkpt lat=\"51.75116613\" lon=\"19.44144697\"><time>2013-05-23T19:23:38.268+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75114805\" lon=\"19.44155805\"><time>2013-05-23T19:23:39.264+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75115006\" lon=\"19.44166065\"><time>2013-05-23T19:23:40.266+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75111624\" lon=\"19.4416921\"><time>2013-05-23T19:23:41.274+02:00</time><ele>237.10000610351563</ele></trkpt><trkpt lat=\"51.75111687\" lon=\"19.44177766\"><time>2013-05-23T19:23:42.290+02:00</time><ele>236.6999969482422</ele></trkpt><trkpt lat=\"51.75112449\" lon=\"19.44188491\"><time>2013-05-23T19:23:43.288+02:00</time><ele>236.5</ele></trkpt><trkpt lat=\"51.75114166\" lon=\"19.44194738\"><time>2013-05-23T19:23:44.292+02:00</time><ele>235.10000610351563</ele></trkpt><trkpt lat=\"51.75114917\" lon=\"19.44201848\"><time>2013-05-23T19:23:45.259+02:00</time><ele>235.0</ele></trkpt><trkpt lat=\"51.75120749\" lon=\"19.44203127\"><time>2013-05-23T19:23:46.279+02:00</time><ele>235.1999969482422</ele></trkpt><trkpt lat=\"51.75120229\" lon=\"19.44206579\"><time>2013-05-23T19:23:47.293+02:00</time><ele>235.5</ele></trkpt><trkpt lat=\"51.75118475\" lon=\"19.44209127\"><time>2013-05-23T19:23:48.292+02:00</time><ele>234.5</ele></trkpt><trkpt lat=\"51.75118786\" lon=\"19.44216042\"><time>2013-05-23T19:23:49.280+02:00</time><ele>233.89999389648438</ele></trkpt><trkpt lat=\"51.7511707\" lon=\"19.44223839\"><time>2013-05-23T19:23:50.284+02:00</time><ele>234.6999969482422</ele></trkpt><trkpt lat=\"51.75117503\" lon=\"19.44227346\"><time>2013-05-23T19:23:51.269+02:00</time><ele>234.60000610351563</ele></trkpt><trkpt lat=\"51.75117399\" lon=\"19.44231944\"><time>2013-05-23T19:23:52.261+02:00</time><ele>234.6999969482422</ele></trkpt><trkpt lat=\"51.7511223\" lon=\"19.44235872\"><time>2013-05-23T19:23:53.297+02:00</time><ele>234.60000610351563</ele></trkpt><trkpt lat=\"51.7502261\" lon=\"19.44277172\"><time>2013-05-23T19:24:10.314+02:00</time><ele>235.6999969482422</ele></trkpt><trkpt lat=\"51.75024707\" lon=\"19.44291009\"><time>2013-05-23T19:24:11.292+02:00</time><ele>235.6999969482422</ele></trkpt><trkpt lat=\"51.75028121\" lon=\"19.44305921\"><time>2013-05-23T19:24:12.256+02:00</time><ele>235.8000030517578</ele></trkpt><trkpt lat=\"51.75027524\" lon=\"19.44317465\"><time>2013-05-23T19:24:13.273+02:00</time><ele>235.8000030517578</ele></trkpt><trkpt lat=\"51.75027682\" lon=\"19.44336657\"><time>2013-05-23T19:24:14.256+02:00</time><ele>235.8000030517578</ele></trkpt><trkpt lat=\"51.7502782\" lon=\"19.4435094\"><time>2013-05-23T19:24:15.276+02:00</time><ele>235.89999389648438</ele></trkpt><trkpt lat=\"51.75027075\" lon=\"19.44402947\"><time>2013-05-23T19:24:18.291+02:00</time><ele>234.5</ele></trkpt><trkpt lat=\"51.75026024\" lon=\"19.44419941\"><time>2013-05-23T19:24:19.285+02:00</time><ele>233.3000030517578</ele></trkpt><trkpt lat=\"51.75027797\" lon=\"19.444378\"><time>2013-05-23T19:24:20.259+02:00</time><ele>232.5</ele></trkpt><trkpt lat=\"51.75030799\" lon=\"19.44453721\"><time>2013-05-23T19:24:21.232+02:00</time><ele>233.10000610351563</ele></trkpt><trkpt lat=\"51.75034821\" lon=\"19.44470404\"><time>2013-05-23T19:24:22.259+02:00</time><ele>233.39999389648438</ele></trkpt><trkpt lat=\"51.75035265\" lon=\"19.44488881\"><time>2013-05-23T19:24:23.256+02:00</time><ele>233.10000610351563</ele></trkpt><trkpt lat=\"51.75036263\" lon=\"19.44504772\"><time>2013-05-23T19:24:24.265+02:00</time><ele>233.10000610351563</ele></trkpt><trkpt lat=\"51.75039897\" lon=\"19.44516761\"><time>2013-05-23T19:24:25.262+02:00</time><ele>234.6999969482422</ele></trkpt><trkpt lat=\"51.75040857\" lon=\"19.44535511\"><time>2013-05-23T19:24:26.261+02:00</time><ele>233.1999969482422</ele></trkpt><trkpt lat=\"51.75041789\" lon=\"19.4455397\"><time>2013-05-23T19:24:27.243+02:00</time><ele>231.5</ele></trkpt><trkpt lat=\"51.75042275\" lon=\"19.44565494\"><time>2013-05-23T19:24:28.252+02:00</time><ele>231.0</ele></trkpt><trkpt lat=\"51.75046951\" lon=\"19.44584815\"><time>2013-05-23T19:24:29.250+02:00</time><ele>232.3000030517578</ele></trkpt><trkpt lat=\"51.75048476\" lon=\"19.44600726\"><time>2013-05-23T19:24:30.241+02:00</time><ele>232.3000030517578</ele></trkpt><trkpt lat=\"51.75050185\" lon=\"19.44616295\"><time>2013-05-23T19:24:31.258+02:00</time><ele>232.3000030517578</ele></trkpt><trkpt lat=\"51.7505169\" lon=\"19.44632294\"><time>2013-05-23T19:24:32.232+02:00</time><ele>232.3000030517578</ele></trkpt><trkpt lat=\"51.75056032\" lon=\"19.44654259\"><time>2013-05-23T19:24:33.246+02:00</time><ele>232.39999389648438</ele></trkpt><trkpt lat=\"51.75057206\" lon=\"19.44673058\"><time>2013-05-23T19:24:34.253+02:00</time><ele>232.10000610351563</ele></trkpt><trkpt lat=\"51.75058742\" lon=\"19.44693768\"><time>2013-05-23T19:24:35.237+02:00</time><ele>233.0</ele></trkpt><trkpt lat=\"51.75060933\" lon=\"19.44711453\"><time>2013-05-23T19:24:36.248+02:00</time><ele>232.89999389648438</ele></trkpt><trkpt lat=\"51.75062728\" lon=\"19.44729705\"><time>2013-05-23T19:24:37.251+02:00</time><ele>233.8000030517578</ele></trkpt><trkpt lat=\"51.75064276\" lon=\"19.44746944\"><time>2013-05-23T19:24:38.246+02:00</time><ele>233.8000030517578</ele></trkpt><trkpt lat=\"51.75066818\" lon=\"19.44762387\"><time>2013-05-23T19:24:39.219+02:00</time><ele>233.5</ele></trkpt><trkpt lat=\"51.75067073\" lon=\"19.44779585\"><time>2013-05-23T19:24:40.239+02:00</time><ele>233.89999389648438</ele></trkpt><trkpt lat=\"51.7506763\" lon=\"19.44796672\"><time>2013-05-23T19:24:41.241+02:00</time><ele>234.60000610351563</ele></trkpt><trkpt lat=\"51.75068872\" lon=\"19.44813452\"><time>2013-05-23T19:24:42.238+02:00</time><ele>234.60000610351563</ele></trkpt><trkpt lat=\"51.75067003\" lon=\"19.44830329\"><time>2013-05-23T19:24:43.225+02:00</time><ele>232.5</ele></trkpt><trkpt lat=\"51.75067723\" lon=\"19.44846336\"><time>2013-05-23T19:24:44.238+02:00</time><ele>232.39999389648438</ele></trkpt><trkpt lat=\"51.75073003\" lon=\"19.44858772\"><time>2013-05-23T19:24:45.229+02:00</time><ele>231.10000610351563</ele></trkpt><trkpt lat=\"51.75074373\" lon=\"19.4487408\"><time>2013-05-23T19:24:46.230+02:00</time><ele>231.1999969482422</ele></trkpt><trkpt lat=\"51.75075744\" lon=\"19.44892486\"><time>2013-05-23T19:24:47.229+02:00</time><ele>230.3000030517578</ele></trkpt><trkpt lat=\"51.75076513\" lon=\"19.44902705\"><time>2013-05-23T19:24:48.243+02:00</time><ele>230.60000610351563</ele></trkpt><trkpt lat=\"51.75077512\" lon=\"19.44915459\"><time>2013-05-23T19:24:49.245+02:00</time><ele>230.6999969482422</ele></trkpt><trkpt lat=\"51.75076321\" lon=\"19.44924058\"><time>2013-05-23T19:24:50.236+02:00</time><ele>230.1999969482422</ele></trkpt><trkpt lat=\"51.75074599\" lon=\"19.4493146\"><time>2013-05-23T19:24:51.237+02:00</time><ele>229.60000610351563</ele></trkpt><trkpt lat=\"51.75077131\" lon=\"19.44938264\"><time>2013-05-23T19:24:52.251+02:00</time><ele>229.3000030517578</ele></trkpt><trkpt lat=\"51.75077436\" lon=\"19.44946002\"><time>2013-05-23T19:24:53.237+02:00</time><ele>229.1999969482422</ele></trkpt><trkpt lat=\"51.75077374\" lon=\"19.44950393\"><time>2013-05-23T19:24:54.237+02:00</time><ele>229.1999969482422</ele></trkpt><trkpt lat=\"51.75077431\" lon=\"19.44957013\"><time>2013-05-23T19:24:55.250+02:00</time><ele>229.3000030517578</ele></trkpt><trkpt lat=\"51.7507976\" lon=\"19.44949375\"><time>2013-05-23T19:24:56.230+02:00</time><ele>228.5</ele></trkpt><trkpt lat=\"51.75078266\" lon=\"19.44948254\"><time>2013-05-23T19:24:57.253+02:00</time><ele>228.3000030517578</ele></trkpt><trkpt lat=\"51.75079395\" lon=\"19.44946752\"><time>2013-05-23T19:24:58.249+02:00</time><ele>228.3000030517578</ele></trkpt><trkpt lat=\"51.75079404\" lon=\"19.44947476\"><time>2013-05-23T19:24:59.228+02:00</time><ele>228.10000610351563</ele></trkpt><trkpt lat=\"51.75078982\" lon=\"19.44946745\"><time>2013-05-23T19:25:00.244+02:00</time><ele>227.60000610351563</ele></trkpt><trkpt lat=\"51.75080356\" lon=\"19.44946978\"><time>2013-05-23T19:25:01.237+02:00</time><ele>228.60000610351563</ele></trkpt><trkpt lat=\"51.75080099\" lon=\"19.4494678\"><time>2013-05-23T19:25:02.237+02:00</time><ele>228.39999389648438</ele></trkpt><trkpt lat=\"51.75080099\" lon=\"19.4494678\"><time>2013-05-23T19:25:03.237+02:00</time><ele>228.39999389648438</ele></trkpt><trkpt lat=\"51.75080042\" lon=\"19.4494681\"><time>2013-05-23T19:25:04.243+02:00</time><ele>228.89999389648438</ele></trkpt><trkpt lat=\"51.75080042\" lon=\"19.4494681\"><time>2013-05-23T19:25:05.237+02:00</time><ele>228.89999389648438</ele></trkpt><trkpt lat=\"51.75080064\" lon=\"19.44946555\"><time>2013-05-23T19:25:06.232+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080066\" lon=\"19.44946527\"><time>2013-05-23T19:25:07.249+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080071\" lon=\"19.44946485\"><time>2013-05-23T19:25:08.244+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080072\" lon=\"19.44946484\"><time>2013-05-23T19:25:09.253+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080072\" lon=\"19.44946484\"><time>2013-05-23T19:25:10.252+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080054\" lon=\"19.44946382\"><time>2013-05-23T19:25:12.252+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.7508052\" lon=\"19.44946087\"><time>2013-05-23T19:25:13.239+02:00</time><ele>229.0</ele></trkpt><trkpt lat=\"51.75080103\" lon=\"19.44946381\"><time>2013-05-23T19:25:14.238+02:00</time><ele>228.8000030517578</ele></trkpt><trkpt lat=\"51.75080106\" lon=\"19.44946411\"><time>2013-05-23T19:25:15.231+02:00</time><ele>228.89999389648438</ele></trkpt><trkpt lat=\"51.75080106\" lon=\"19.44946411\"><time>2013-05-23T19:25:16.248+02:00</time><ele>228.89999389648438</ele></trkpt><trkpt lat=\"51.75080957\" lon=\"19.44946162\"><time>2013-05-23T19:25:17.253+02:00</time><ele>229.10000610351563</ele></trkpt><trkpt lat=\"51.75080974\" lon=\"19.44946151\"><time>2013-05-23T19:25:18.237+02:00</time><ele>229.10000610351563</ele></trkpt><trkpt lat=\"51.75080982\" lon=\"19.44946151\"><time>2013-05-23T19:25:19.252+02:00</time><ele>229.10000610351563</ele></trkpt><trkpt lat=\"51.75080995\" lon=\"19.44946187\"><time>2013-05-23T19:25:20.235+02:00</time><ele>229.10000610351563</ele></trkpt><trkpt lat=\"51.75080979\" lon=\"19.44946136\"><time>2013-05-23T19:25:21.212+02:00</time><ele>229.0</ele></trkpt><trkpt lat=\"51.75080979\" lon=\"19.44946136\"><time>2013-05-23T19:25:22.251+02:00</time><ele>229.0</ele></trkpt><trkpt lat=\"51.75080952\" lon=\"19.44946245\"><time>2013-05-23T19:25:23.235+02:00</time><ele>229.10000610351563</ele></trkpt><trkpt lat=\"51.75079869\" lon=\"19.44947789\"><time>2013-05-23T19:25:24.247+02:00</time><ele>229.1999969482422</ele></trkpt><trkpt lat=\"51.75072571\" lon=\"19.44947313\"><time>2013-05-23T19:25:25.238+02:00</time><ele>227.10000610351563</ele></trkpt><trkpt lat=\"51.75069834\" lon=\"19.44949706\"><time>2013-05-23T19:25:26.252+02:00</time><ele>227.0</ele></trkpt><trkpt lat=\"51.75055098\" lon=\"19.44972097\"><time>2013-05-23T19:25:27.224+02:00</time><ele>225.10000610351563</ele></trkpt><trkpt lat=\"51.75042427\" lon=\"19.44976093\"><time>2013-05-23T19:25:28.235+02:00</time><ele>224.5</ele></trkpt><trkpt lat=\"51.75034446\" lon=\"19.44977844\"><time>2013-05-23T19:25:29.226+02:00</time><ele>224.1999969482422</ele></trkpt><trkpt lat=\"51.75028354\" lon=\"19.44983164\"><time>2013-05-23T19:25:30.220+02:00</time><ele>224.0</ele></trkpt><trkpt lat=\"51.75019694\" lon=\"19.44988782\"><time>2013-05-23T19:25:31.243+02:00</time><ele>224.5</ele></trkpt><trkpt lat=\"51.75009088\" lon=\"19.44992711\"><time>2013-05-23T19:25:32.221+02:00</time><ele>225.10000610351563</ele></trkpt><trkpt lat=\"51.74991452\" lon=\"19.44987618\"><time>2013-05-23T19:25:33.231+02:00</time><ele>225.5</ele></trkpt><trkpt lat=\"51.7497667\" lon=\"19.44988866\"><time>2013-05-23T19:25:34.231+02:00</time><ele>225.60000610351563</ele></trkpt><trkpt lat=\"51.74960972\" lon=\"19.44989622\"><time>2013-05-23T19:25:35.222+02:00</time><ele>226.10000610351563</ele></trkpt><trkpt lat=\"51.74948003\" lon=\"19.44989807\"><time>2013-05-23T19:25:36.225+02:00</time><ele>226.1999969482422</ele></trkpt><trkpt lat=\"51.74933314\" lon=\"19.4499169\"><time>2013-05-23T19:25:37.225+02:00</time><ele>226.60000610351563</ele></trkpt><trkpt lat=\"51.74921347\" lon=\"19.44991359\"><time>2013-05-23T19:25:38.219+02:00</time><ele>226.5</ele></trkpt><trkpt lat=\"51.74909957\" lon=\"19.44997894\"><time>2013-05-23T19:25:39.266+02:00</time><ele>226.39999389648438</ele></trkpt><trkpt lat=\"51.74897665\" lon=\"19.44996192\"><time>2013-05-23T19:25:40.217+02:00</time><ele>226.39999389648438</ele></trkpt><trkpt lat=\"51.74885763\" lon=\"19.44998471\"><time>2013-05-23T19:25:41.224+02:00</time><ele>226.3000030517578</ele></trkpt><trkpt lat=\"51.74872328\" lon=\"19.45000728\"><time>2013-05-23T19:25:42.221+02:00</time><ele>225.6999969482422</ele></trkpt><trkpt lat=\"51.74855302\" lon=\"19.44999526\"><time>2013-05-23T19:25:43.220+02:00</time><ele>224.89999389648438</ele></trkpt><trkpt lat=\"51.74842717\" lon=\"19.45001569\"><time>2013-05-23T19:25:44.237+02:00</time><ele>224.8000030517578</ele></trkpt><trkpt lat=\"51.74825188\" lon=\"19.4500752\"><time>2013-05-23T19:25:45.227+02:00</time><ele>224.8000030517578</ele></trkpt><trkpt lat=\"51.74808625\" lon=\"19.45014489\"><time>2013-05-23T19:25:46.288+02:00</time><ele>224.8000030517578</ele></trkpt><trkpt lat=\"51.7479678\" lon=\"19.45016135\"><time>2013-05-23T19:25:47.231+02:00</time><ele>224.5</ele></trkpt><trkpt lat=\"51.74781826\" lon=\"19.45020327\"><time>2013-05-23T19:25:48.231+02:00</time><ele>224.5</ele></trkpt><trkpt lat=\"51.74767614\" lon=\"19.45020488\"><time>2013-05-23T19:25:49.238+02:00</time><ele>225.1999969482422</ele></trkpt><trkpt lat=\"51.7475389\" lon=\"19.45022862\"><time>2013-05-23T19:25:50.237+02:00</time><ele>225.10000610351563</ele></trkpt><trkpt lat=\"51.74744666\" lon=\"19.45024757\"><time>2013-05-23T19:25:51.254+02:00</time><ele>223.89999389648438</ele></trkpt><trkpt lat=\"51.74733487\" lon=\"19.45025421\"><time>2013-05-23T19:25:52.333+02:00</time><ele>223.6999969482422</ele></trkpt><trkpt lat=\"51.74724841\" lon=\"19.45028695\"><time>2013-05-23T19:25:53.244+02:00</time><ele>224.3000030517578</ele></trkpt><trkpt lat=\"51.7471251\" lon=\"19.45031366\"><time>2013-05-23T19:25:54.248+02:00</time><ele>224.5</ele></trkpt><trkpt lat=\"51.74705071\" lon=\"19.45031147\"><time>2013-05-23T19:25:55.287+02:00</time><ele>224.0</ele></trkpt><trkpt lat=\"51.74695024\" lon=\"19.45032094\"><time>2013-05-23T19:25:56.255+02:00</time><ele>222.60000610351563</ele></trkpt><trkpt lat=\"51.74687815\" lon=\"19.45032889\"><time>2013-05-23T19:25:57.266+02:00</time><ele>222.39999389648438</ele></trkpt><trkpt lat=\"51.74679302\" lon=\"19.45034092\"><time>2013-05-23T19:25:58.257+02:00</time><ele>222.39999389648438</ele></trkpt><trkpt lat=\"51.74676093\" lon=\"19.45028991\"><time>2013-05-23T19:25:59.258+02:00</time><ele>222.0</ele></trkpt><trkpt lat=\"51.74672201\" lon=\"19.4502099\"><time>2013-05-23T19:26:00.279+02:00</time><ele>222.5</ele></trkpt><trkpt lat=\"51.74665688\" lon=\"19.45017396\"><time>2013-05-23T19:26:01.279+02:00</time><ele>222.5</ele></trkpt><trkpt lat=\"51.74661082\" lon=\"19.45011265\"><time>2013-05-23T19:26:02.275+02:00</time><ele>222.3000030517578</ele></trkpt><trkpt lat=\"51.74677268\" lon=\"19.45014344\"><time>2013-05-23T19:26:03.274+02:00</time><ele>222.8000030517578</ele></trkpt><trkpt lat=\"51.74683538\" lon=\"19.45010158\"><time>2013-05-23T19:26:04.286+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.74687423\" lon=\"19.45010843\"><time>2013-05-23T19:26:05.274+02:00</time><ele>223.39999389648438</ele></trkpt><trkpt lat=\"51.74686297\" lon=\"19.45007528\"><time>2013-05-23T19:26:06.275+02:00</time><ele>223.3000030517578</ele></trkpt><trkpt lat=\"51.74689465\" lon=\"19.44999904\"><time>2013-05-23T19:26:07.298+02:00</time><ele>224.1999969482422</ele></trkpt><trkpt lat=\"51.74706978\" lon=\"19.45008482\"><time>2013-05-23T19:26:08.299+02:00</time><ele>220.6999969482422</ele></trkpt><trkpt lat=\"51.74707646\" lon=\"19.45005513\"><time>2013-05-23T19:26:09.291+02:00</time><ele>220.6999969482422</ele></trkpt><trkpt lat=\"51.74706215\" lon=\"19.44998243\"><time>2013-05-23T19:26:10.290+02:00</time><ele>221.6999969482422</ele></trkpt><trkpt lat=\"51.74704355\" lon=\"19.44993709\"><time>2013-05-23T19:26:11.289+02:00</time><ele>222.60000610351563</ele></trkpt><trkpt lat=\"51.74703586\" lon=\"19.44993657\"><time>2013-05-23T19:26:12.289+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.74703674\" lon=\"19.44991397\"><time>2013-05-23T19:26:13.312+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.74700946\" lon=\"19.44992338\"><time>2013-05-23T19:26:14.309+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.74700136\" lon=\"19.44992837\"><time>2013-05-23T19:26:15.313+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.7469471\" lon=\"19.44993528\"><time>2013-05-23T19:26:16.319+02:00</time><ele>223.0</ele></trkpt><trkpt lat=\"51.74688384\" lon=\"19.4499135\"><time>2013-05-23T19:26:17.308+02:00</time><ele>222.89999389648438</ele></trkpt><trkpt lat=\"51.74685697\" lon=\"19.44992019\"><time>2013-05-23T19:26:18.309+02:00</time><ele>222.89999389648438</ele></trkpt><trkpt lat=\"51.74675124\" lon=\"19.44994865\"><time>2013-05-23T19:26:22.301+02:00</time><ele>223.1999969482422</ele></trkpt><trkpt lat=\"51.74673201\" lon=\"19.44994841\"><time>2013-05-23T19:26:23.322+02:00</time><ele>223.1999969482422</ele></trkpt><trkpt lat=\"51.74667852\" lon=\"19.44998414\"><time>2013-05-23T19:26:24.316+02:00</time><ele>223.3000030517578</ele></trkpt><trkpt lat=\"51.74666397\" lon=\"19.4499959\"><time>2013-05-23T19:26:25.316+02:00</time><ele>223.3000030517578</ele></trkpt><trkpt lat=\"51.74662478\" lon=\"19.44998984\"><time>2013-05-23T19:26:26.323+02:00</time><ele>223.3000030517578</ele></trkpt></trkseg></trk></gpx>");
            routetDao.createOrUpdate(route);
            
            Route route2 = new Route();
            route2.setName("Trasa z dnia 11-04-2013");
            route2.setCalories(182);
            route2.setDate(new DateTime(2013,04,11,0,0));
            route2.setWorkoutTime(8204584);
            route2.setActivity(WorkoutType.RUNNING);
            route2.setXml(route.getXml());
            routetDao.createOrUpdate(route2);
            
            Route route3 = new Route();
            route3.setName("Trasa z dnia 08-03-2013");
            route3.setCalories(486);
            route3.setDate(new DateTime(2013,03,8,0,0));
            route3.setActivity(WorkoutType.RUNNING);
            route3.setXml(route.getXml());
            routetDao.createOrUpdate(route3);
            
            Route route4 = new Route();
            route4.setName("Trasa z dnia 01-04-2013");
            route4.setCalories(584);
            route4.setDate(new DateTime(2013,04,1,0,0));
            route4.setActivity(WorkoutType.RUNNING);
            route4.setXml(route.getXml());
            routetDao.createOrUpdate(route4);
            
            Route route5 = new Route();
            route5.setName("Trasa z dnia 09-04-2013");
            route5.setCalories(584);
            route5.setDate(new DateTime(2013,04,9,0,0));
            route5.setActivity(WorkoutType.WALKING);
            route5.setXml(route.getXml());
            routetDao.createOrUpdate(route5);
            
            Route route6 = new Route();
            route6.setName("Trasa z dnia 07-04-2013");
            route6.setCalories(584);
            route6.setDate(new DateTime(2013,04,7,0,0));
            route6.setActivity(WorkoutType.HIKING);
            route6.setXml(route.getXml());
            routetDao.createOrUpdate(route6);
            
            Route route7 = new Route();
            route7.setName("Trasa z dnia 04-04-2013");
            route7.setCalories(584);
            route7.setDate(new DateTime(2013,04,4,0,0));
            route7.setActivity(WorkoutType.RUNNING);
            route7.setXml(route.getXml());
            routetDao.createOrUpdate(route7);
            
            Route route8 = new Route();
            route8.setName("Trasa z dnia 17-04-2013");
            route8.setCalories(584);
            route8.setDate(new DateTime(2013,04,17,0,0));
            route8.setActivity(WorkoutType.RUNNING);
            route8.setXml(route.getXml());
            routetDao.createOrUpdate(route8);
            
            Route route9 = new Route();
            route9.setName("Trasa z dnia 27-04-2013");
            route9.setCalories(584);
            route9.setDate(new DateTime(2013,04,27,0,0));
            route9.setActivity(WorkoutType.RUNNING);
            route9.setXml(route.getXml());
            routetDao.createOrUpdate(route9);
            
            Route route10 = new Route();
            route10.setName("Trasa z dnia 24-04-2013");
            route10.setCalories(584);
            route10.setDate(new DateTime(2013,04,24,0,0));
            route10.setActivity(WorkoutType.RUNNING);
            route10.setXml(route.getXml());
            routetDao.createOrUpdate(route10);
            
            Log.i(TAG, "utworzono nowe rekodry w metodzie " + methodName + " TIME: "
                    + (System.currentTimeMillis() - millis));
        }
        catch (SQLException e) {
            Log.e(TAG, "Nie mozna utowrzyc bazy danych", e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        
    }
    
    /**
     * Zwraca DAO do typu Route.
     */
    public Dao<Route, Integer> getRouteDao() throws SQLException {
        if (routetDao == null) {
            routetDao = getDao(Route.class);
        }
        return routetDao;
    }
    
    /**
     * Zamyka polaczenia i czysci cache bazy oraz obiekty DAO.
     */
    @Override
    public void close() {
        final String methodName = "close";
        super.close();
        routetDao = null;
        Log.i(TAG, methodName + " - zamknieto polaczenia.");
    }
}