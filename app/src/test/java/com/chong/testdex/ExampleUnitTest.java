package com.chong.testdex;

import android.app.Activity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testCMD(){
        CMD.execCommand(new String[]{"adb devices"},false);
    }

    @Test
    public void testPint(){
        System.out.println("1 \n 2\n 3\n");
    }


    @Test
    public void testInt() {

        System.out.println(1001&1);
        Person p = new Man();
        try{
            p.doSome();
        }catch (Exception e){
            System.out.println("error");
        }
        System.out.println("end");

    }

    static class Man implements Person{

        @Override
        public void doSome() {
            int a = 100/0;
        }
    }
    interface Person{
        void doSome();
    }

    @Test
    public void testMatcher(){

        String xml = "<audio>1</audio><fs>1069049</fs><super>0</super><sl>0</sl></fi><fi><id>322002</id><name>hd</name><lmt>0</lmt><sb>1</sb><cname>高清;(480P)</cname><br>54</br><profile>4</profile><drm>0</drm><video>2</video><audio>1</audio><fs>1876866</fs><super>0</super><sl>1</sl></fi><fi><id>322003</id><name>shd</name><lmt>0</lmt><sb>1</sb><cname>超清;(720P)</cname><br>59</br><profile>4</profile><drm>0</drm><video>2</video><audio>1</audio><fs>3589549</fs><super>0</super><sl>0</sl></fi></fl><fp2p>2</fp2p><hs>0</hs><ip>219.140.43.172</ip><ls>0</ls><preview>33</preview><s>o</s><sfl><cnt>0</cnt></sfl><tm>1553514181</tm><vl><cnt>1</cnt><vi><br>54</br><ch>0</ch><ct>21600</ct><dm>1</dm><drm>0</drm><dsb>0</dsb><enc>0</enc><fc>1</fc><fmd5>0796bdfd12da03431c4047b5e3285597</fmd5><fn>t08400el7p5.322002.ts</fn><fps>23.976</fps><fs>1876866</fs><fst>5</fst><head>0</head><hevc>1</hevc><hfs>2158395</hfs><iflag>0</iflag><keyid>t08400el7p5.322002</keyid><lnk>t08400el7p5</lnk><logo>0</logo><mst>8</mst><share>1</share><sig>01c7f298ba29567c1e3cb3b4e8548f6dc14c41f5</sig><st>2</st><tail>0</tail><targetid>3732455975</targetid><td>33.62</td><ti>宝贝老板：自从家里有了二胎后，提姆的位置渐渐被取代掉</ti><tie>0</tie><type>24</type><ul><ui><url>http://58.49.159.21/omts.tc.qq.com/Aac4uafcweFM-PY57yJJFApdE9k4K1ZREsJkjVEZK8Oo/uwMROfz0r5yIYaQXGdGnC2df57bz-XqpOBa-fWc0px4LWSQN/fdZeJqZ_DJ6OFqHUOpAw5P8oY7Gv3XrWdDHv1zBAgkQxu53vP3DVAidhC1_jrzGCS3vE16-CCNiVgXPUtKaN0mY-X_g4EHgnxI_bnTh_RbYLrIzudXEeexfpT8G-3loIZ6C3rP3d86i1yQMzIUbAKF1CWDLjN8si/t08400el7p5.322002.ts.m3u8?ver=4</url><vt>2806</vt><dtc>0</dtc><dt>2</dt></ui><ui><url>http://58.49.159.22/omts.tc.qq.com/Aac4uafcweFM-PY57yJJFApdE9k4K1ZREsJkjVEZK8Oo/uwMROfz0r5yIYaQXGdGnCmdf57Zvpe0oZmF3kXibaeKRipcw/fdZeJqZ_DJ6OFqHUOpAw5P8oY7Gv3XrWdDHv1zBAgkQxu53vP3DVAidhC1_jrzGCS3vE16-CCNiVgXPUtKaN0mY-X_g4EHgnxI_bnTh_RbYLrIzudXEeexfpT8G-3loIZ6C3rP3d86i1yQMzIUbAKF1CWDLjN8si/t08400el7p5.322002.ts.m3u8?ver=4</url><vt>2806</vt><dtc>0</dtc><dt>2</dt></ui><ui><url>http://58.49.159.23/omts.tc.qq.com/Aac4uafcweFM-PY57yJJFApdE9k4K1ZREsJkjVEZK8Oo/uwMROfz0r5yIYaQXGdGnCWdf57YJQFWrhPktpVhrOuc-_kJ2/fdZeJqZ_DJ6OFqHUOpAw5P8oY7Gv3XrWdDHv1zBAgkQxu53vP3DVAidhC1_jrzGCS3vE16-CCNiVgXPUtKaN0mY-X_g4EHgnxI_bnTh_RbYLrIzudXEeexfpT8G-3loIZ6C3rP3d86i1yQMzIUbAKF1CWDLjN8si/t08400el7p5.322002.ts.m3u8?ver=4</url><vt>2806</vt><dtc>0</dtc><dt>2</dt></ui><ui><url>http://defaultts.tc.qq.com/defaultts.tc.qq.com/uwMROfz0r5yIYaQXGdGnDGdf57aibpwvo1HD-Tl7z-nPYz28/fdZeJqZ_DJ6OFqHUOpAw5P8oY7Gv3XrWdDHv1zBAgkQxu53vP3DVAidhC1_jrzGCS3vE16-CCNiVgXPUtKaN0mY-X_g4EHgnxI_bnTh_RbYLrIzudXEeexfpT8G-3loIZ6C3rP3d86i1yQMzIUbAKF1CWDLjN8si/t08400el7p5.322002.ts.m3u8?ver=4</url><vt>2800</vt><dtc>0</dtc><dt>2</dt></ui></ul><vh>486</vh><vid>t08400el7p5</vid><videotype>3</videotype><vr>0</vr><vst>2</vst><vw>864</vw><wh>1.7777778</wh><wl><wi><id>43</id><x>22</x><y>22</y><w>78</w><h>54</h><a>100</a><md5>9048063bba5641";

//        String pattern = "^(?!.*(<>)";
//        Pattern compile = Pattern.compile(pattern);


        String pattern = "<ul><ui><url>(http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?)";
        Pattern compile = Pattern.compile(pattern);

        Matcher matcher = compile.matcher(xml);
        List<String> list = new ArrayList<String>();
        while(matcher.find()) {
            String url = matcher.group(1);
            list.add(url);
            System.out.println("chong url:"+url);

        }
        System.out.println(list.get(0).length());


    }
    @Test
    public void testSub(){
        String videoWebUri="";
//        int i = 1;
//        while (videoWebUri.length()<4){
//            videoWebUri += i;
//        }
        System.out.println(videoWebUri);
        System.out.println(videoWebUri.toCharArray().length);
        videoWebUri = videoWebUri.substring(0,videoWebUri.length()>500?500:videoWebUri.length());
        System.out.println(videoWebUri);
        System.out.println(videoWebUri.length());

        System.out.println(0l/0);

    }

    @Test
    public void testNets(){
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}