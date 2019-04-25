package com.example;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class NewsPaperDetailsActivity extends AppCompatActivity {

    private String title;
    private String content = "　　 发现培养选拔优秀年轻干部是加强领导班子和干部队伍建设的一项基础性工程，是关系党的事业后继有人和国家长治久安的重大战略任务\n" +
            "　　 新时代新使命要求我们切实增强责任感和紧迫感，以更长远的眼光、更有效的举措，及早发现、及时培养、源源不断选拔使用适应新时代要求的优秀年轻干部，为党和国家事业发展注入新的生机活力\n" +
            "　　 新华社北京6月29日电 中共中央政治局6月29日召开会议，审议《关于适应新时代要求大力发现培养选拔优秀年轻干部的意见》。中共中央总书记习近平主持会议。\n" +
            "　　 议强调，发现培养选拔优秀年轻干部是加强领导班子和干部队伍建设的一项基础性工程，是关系党的事业后继有人和国家长治久安的重大战略任务。党的十八大以来，我们坚决落实好干部标准，破除唯年龄偏向，改进后备干部工作，优化干部成长路径，推动落实常态化配备目标，年轻干部工作取得了显著成效。\n" +
            "　　 会议指出，当前，中国特色社会主义进入新时代，我们党团结带领人民进行伟大斗争、建设伟大工程、推进伟大事业、实现伟大梦想，关键在于建设一支高素质专业化干部队伍，归根到底在于培养选拔一批又一批优秀年轻干部接续奋斗。新时代新使命要求我们切实增强责任感和紧迫感，以更长远的眼光、更有效的举措，及早发现、及时培养、源源不断选拔使用适应新时代要求的优秀年轻干部，为党和国家事业发展注入新的生机活力。要按照做好新时代年轻干部工作的总体思路、目标任务、政策措施，统一思想、提高认识，进一步推进年轻干部工作制度化、规范化、常态化。\n" +
            "　　 会议强调，要着眼“两个一百年”奋斗目标，着眼推进国家治理体系和治理能力现代化，着眼党的事业后继有人、兴旺发达，努力建设一支忠实贯彻习近平新时代中国特色社会主义思想、全心全意为人民服务，适应新使命新任务新要求、经得起风浪考验，数量充足、充满活力的高素质专业化年轻干部队伍。\n" +
            "　　 会议指出，大力发现培养选拔优秀年轻干部，要全面贯彻党的十九大和十九届二中、三中全会精神，以习近平新时代中国特色社会主义思想为指导，紧紧围绕统筹推进“五位一体”总体布局和协调推进“四个全面”战略布局，落实好干部标准，遵循干部成长规律，按照拓宽来源、优化结构、改进方式、提高质量的要求，进一步创新理念、创新思路、创新模式，以大力发现培养为基础，以强化实践锻炼为重点，以确保选准用好为根本，以从严管理监督为保障，健全完善年轻干部选拔、培育、管理、使用环环相扣又统筹推进的全链条机制，形成优秀年轻干部不断涌现的生动局面，把各方面各领域优秀领导人才聚集到执政骨干队伍中来，为决胜全面建成小康社会、夺取新时代中国特色社会主义伟大胜利、实现中华民族伟大复兴的中国梦提供充足干部储备和人才保证。\n" +
            "　　 会议要求，各级党委（党组）要增强大局意识和全局观念，把年轻干部工作摆上重要议事日程，切实抓紧抓好。要建立以党委（党组）主要负责同志为第一责任人的工作责任制，一级抓一级，一级带一级，逐级负责，层层抓落实，把发现培养选拔年轻干部工作实效作为党建工作考核的重要内容。\n" +
            "　　 会议还研究了其他事项。";
    private TextView titleText;
    private TextView contentText;
    private ImageView imageViewBack;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_paper_details);


        titleText = (TextView) findViewById(R.id.title);
        contentText = (TextView) findViewById(R.id.content);
        imageViewBack = (ImageView) findViewById(R.id.news_details_title_back);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
//        content = bundle.getString("content");

        titleText.setText(title);
        contentText.setText(content);

        imageViewBack.setOnClickListener(mOnclickListener);
    }



    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NewsPaperDetailsActivity.this.setResult(13);
            NewsPaperDetailsActivity.this.finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        NewsPaperDetailsActivity.this.setResult(13);
        NewsPaperDetailsActivity.this.finish();
        return false;
    }
}
