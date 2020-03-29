delete from movie;
delete from genre;
delete from company;
delete from review;

drop table movie;
drop table genre;
drop table company;
drop table review;

create table movie (
id SERIAL PRIMARY KEY,
title varchar, 
genre int,
company int,
year int,
image varchar, 
abst varchar
);

create table genre (
id SERIAL PRIMARY KEY,
name varchar
);

create table company (
id SERIAL PRIMARY KEY,
name varchar
);

create table review (
id SERIAL PRIMARY KEY,
m_id int,
comment varchar,
rank varchar
);

insert into movie (title, genre, company, year, image, abst) values ('ゴットファーザー', 1, 1, 1972, 'movie1.jpg', 'マフィアの世界を克明に描きベストセラーとなったマリオ・プーゾの同名小説をフランシス・コッポラが映画化した一大叙事詩。シシリーからアメリカに移住し、一代で財を成したドン・コルレオーネ。三男のマイケルはひとり堅気な人生を送ろうとしていたが、敵対するファミリーにドンが襲われ重傷を負った時、彼は報復を決意する。そしてニューヨークは抗争の場と化していった……。');
insert into movie (title, genre, company, year, image, abst) values ('ショーシャンクの空に', 2, 2, 1995, 'movie2.jpg', '妻とその愛人を射殺したかどでショーシャンク刑務所送りとなった銀行家アンディ。初めは戸惑っていたが、やがて彼は自ら持つ不思議な魅力ですさんだ受刑者達の心を掴んでゆく。そして20年の歳月が流れた時、彼は冤罪を晴らす重要な証拠をつかむのだが……。 ');
insert into movie (title, genre, company, year, image, abst) values ('バットマン', 4, 2, 1989, 'movie3.jpg', 'DCコミックスの誇るスーパー・ヒーローを巨費を投じて映画化した大作。ゴッサム・シティの闇に跳梁し悪を粉砕するバットマンと犯罪組織の新ボス・ジョーカーとの戦い。単純明解な娯楽編にはせずにひねった作品を目指していたのか、バットマンの誕生の秘密やジョーカーの設定などかなり暗めで、マニアックな仕上がりになっている。ニコルソン怪演。 ');
insert into movie (title, genre, company, year, image, abst) values ('マトリックス', 1, 2, 1999, 'movie4.jpg', 'ウォシャウスキー・ブラザースによる新感覚のアクション巨編。ニューヨークの会社でしがないコンピュータプログラマーとして働くトマス・アンダーソンには、裏世界の凄腕ハッカー“ネオ”というもうひとつの顔があった。ある日、“ネオ”はディスプレイに現れた不思議なメッセージに導かれるまま、謎の美女トリニティと出会う。そして彼女の手引きによってある人物と接見することになった……。');
insert into movie (title, genre, company, year, image, abst) values ('ハリーポッターと賢者の石', 4, 2, 2001, 'movie5.jpg', '両親の死後、親戚の家に預けられたハリー・ポッター少年。そこでは階段下の物置部屋をあてがわれ、何かとこき使われる毎日。そんなある日、ハリーの11歳の誕生日に一通の手紙が届いた。中身はなんと、魔法魔術学校の入学許可証だった。実は、ハリーの両親は優秀な魔法使いだったのだ。手紙に導かれるままホグワーツ魔法魔術学校にたどり着いたハリーは、さっそく魔法使いになるための勉強を始める。ロンとハーマイオニーという友達もでき、楽しい毎日を送るハリーだったが、やがて学校に隠された驚くべき秘密に気づくのだった……。');
insert into movie (title, genre, company, year, image, abst) values ('チャーリーとチョコレート工場', 3, 2, 2005, 'movie6.jpg', '40年以上も世界でベストセラーになり続けている「チャーリーとチョコレート工場」を映像化したファンタジック・ムービー。鬼才ティム・バートン監督とジョニー・デップがタッグを組み独特の世界観を作り出した。共演に『ネバーランド』のピーター役で天才子役として世界中から注目を集めたフレディー・ハイモア。原作には描かれていない工場長ウィリー・ウォンカの子供時代も明らかにされる。工場の従業員である小人のウンパ・ルンパのダンスといろいろなジャンル音楽で奏でられるコーラスは見逃せない。');
insert into movie (title, genre, company, year, image, abst) values ('ホーム・アローン', 3, 3, 1991, 'movie7.jpg', 'ある一家が総出でパリに行くことになった。ところが息子のケビンだけは、出発のどさくさで、独り屋敷に取り残されてしまう。初めての一人暮らしに浮きたつケビン。そんなおり、留守だと思った二人組の泥棒が屋敷を狙ってきた。ケビンは家を守るため、男たちの撃退作戦に出るが……。M・カルキン坊やを一躍人気者にしたドロボー撃退ムービー。 ');
insert into movie (title, genre, company, year, image, abst) values ('スターウォーズ エピソード4/新たなる野望', 4, 3, 1977, 'movie8.jpg', '遠い昔、遙か彼方の銀河では帝国軍の独裁体制が敷かれていた。反乱の機会をうかがう惑星アルデラーンのレイア姫は暗黒卿ダース・ベイダーに捕らえられるが、その寸前に二体のドロイドR2－D2とC－3POを砂漠の惑星タトゥーインに送り込む。偶然にもそのドロイドを手に入れた青年ルークは、ジェダイ騎士団の一人オビ・ワン・ケノービや密輸船ミレニアム・ファルコンの船長ハン・ソロたちと共に、反乱軍と帝国軍の闘いに巻き込まれていく。');
insert into movie (title, genre, company, year, image, abst) values ('ナイト ミュージアム', 3, 3, 2006, 'movie9.jpg', '真夜中の自然史博物館を舞台に、そこで働く夜間警備員の男が体験する不可思議な現象を描いた爆笑コメディ。『ピンク・パンサー』のショーン・レヴィが監督を務め、魔法で次々と動き出す博物館の展示物を止めようとする主人公の奔走をテンポよく演出。夜間警備員に『ミート・ザ・ペアレンツ』のベン・スティラー、人形のアメリカ大統領セオドア・ルーズベルトにロビン・ウィリアムズがふんし、新旧実力派コメディアンが絶妙な掛け合いを見せる。恐竜が闊歩し、かつての英雄たちが動き出す映像も必見。 ');
insert into movie (title, genre, company, year, image, abst) values ('天使にラブソングを', 3, 4, 1992, 'movie10.jpg', 'ウーピー・ゴールドバーグの人気を不動の物にしたミュージック・コメディ。とある殺人現場を目撃したために、組織に命を狙われるようになった売れないクラブ歌手が、裁判の日まで修道院でかくまわれるハメに。しかし、元々下町で下品に育った彼女がそんなに神聖にできるはずもなく、やがて、聖歌隊をゴスペル風に改造し……');
insert into movie (title, genre, company, year, image, abst) values ('アルマゲドン', 4, 4, 2001, 'movie11.jpg', '地球への衝突コースを取る小惑星が発見された。もしも、テキサス州の大きさにも匹敵するその小惑星が地球に激突すれば、人類の破滅は免れない。これを回避する方法はただひとつ、小惑星内部に核爆弾を設置し、内側から破壊するしかない。そしてその任務に選ばれたのは石油採掘のスペシャリストたちだった。刻々と迫る滅亡へのカウントダウンの中、人類の運命を委ねられた14人の男たちは小惑星へと飛び立った！');
insert into movie (title, genre, company, year, image, abst) values ('ジョーズ', 6, 5, 1975, 'movie12.jpg', '平和な海水浴場に突如出現した巨大な人喰い鮫。観光地としての利益を求める市当局によって対応が遅れ犠牲者の数は増すばかりとなるが、遂に警察署長ブロディと漁師クイント、海洋学者フーパーの三人の男が鮫退治に乗り出す。ピーター・ベンチリーのベストセラーを若きスピルバーグが映画化したメガヒット・ムービー。');
insert into movie (title, genre, company, year, image, abst) values ('ジュラシック・パーク', 4, 5, 1993, 'movie13.jpg', '大富豪ジョン・ハモンドの招待で、古生物学者グラントとサトラー、そして数学者マルコムが南米コスタリカの沖合いに浮かぶ島を訪れた。そこは太古の琥珀に閉じ込められたDNAから遺伝子工学によって蘇った恐竜たちが生息する究極のアミューズメント・パークだったのだ。だがオープンを控えたその“ジュラシック・パーク”に次々とトラブルが襲いかかる。嵐の迫る中、ついに檻から解き放たれた恐竜たちは一斉に人間に牙を剥き始めた。');
insert into movie (title, genre, company, year, image, abst) values ('E.T.', 4, 5, 1982, 'movie14.jpg', '地球の探査にやって来て一人取り残された異星人と少年の交流を暖かく描き上げたSFファンタジー。森の中に静かに降り立つ異星の船から現れる宇宙人たち。だが彼らの地球植物の調査は人間たちの追跡によって中断される。宇宙船は急いで空に舞い上がるが一人の異星人が取り残されていた。森林にほど近い郊外に住む少年エリオットは裏庭でその異星人と遭遇、彼をかくまう事にする。兄と妹を巻き込んで、ETと名付けられたその異星人との交流が始まったが、ETの存在を知っているのはエリオットたちだけではなかった……。');
insert into movie (title, genre, company, year, image, abst) values ('ベイマックス', 5, 6, 2014, 'movie15.jpg', '西洋と東洋の文化がマッチし、最先端技術分野の先駆者たちが数多く住んでいるサンフランソウキョウ。そこに暮らしている14歳の天才児ヒロは、たった一人の肉親であった兄のタダシを亡くしてしまう。深い悲しみに沈む彼だったが、その前にタダシが開発した風船のように膨らむ柔らかくて白い体のロボット、ベイマックスが現れる。苦しんでいる人々を回復させるためのケアロボット・ベイマックスの優しさに触れて生気がよみがえってきたヒロは、タダシの死に不審なものを感じて真相を追い求めようと動き出す。');
insert into movie (title, genre, company, year, image, abst) values ('塔の上のラプンツェル', 5, 6, 2010, 'movie16.jpg', '深い森に囲まれた高い塔の上から18年間一度も外に出たことがないラプンツェルは、母親以外の人間に会ったこともなかった。ある日、お尋ね者の大泥棒フリンが、追手を逃れて塔に侵入してくるが、ラプンツェルの魔法の髪に捕らえられてしまう。しかし、この偶然の出会いはラプンツェルの秘密を解き明かす冒険の始まりのきっかけとなり……');
insert into movie (title, genre, company, year, image, abst) values ('アナと雪の女王', 5, 6, 2013, 'movie17.jpg', 'エルサとアナは美しき王家の姉妹。しかし、触ったものを凍らせてしまう秘められた力を持つ姉エルサが、真夏の王国を冬の世界に変化させてしまった。行方不明になったエルサと王国を何とかすべく、妹のアナは山男のクリストフ、トナカイのスヴェン、夏に憧れる雪だるまのオラフと一緒に山の奥深くへと入っていく。');
insert into movie (title, genre, company, year, image, abst) values ('トイ・ストーリー', 5, 7, 1995, 'movie18.jpg', 'カウボーイ人形のウッディはアンディ少年の大のお気に入り。だがそれも誕生日プレゼントでアクション人形バズ・ライトイヤーを手にするまでの事だった。NO．1の座を奪われたウッディは何とかバズをこらしめようとするが、バズはバズで自分が本物のスペース・レンジャーだと思い込んでいる有り様。そんな二人がふとしたいざこざから外の世界に飛び出してしまう。なんとか我が家へ帰還しようとする二人だが、なんとアンディの隣に住む悪ガキのシドに捕まってしまった……。');
insert into movie (title, genre, company, year, image, abst) values ('モンスターズ・インク', 5, 7, 2001, 'movie19.jpg', '子ども部屋のクローゼットの向こう側に広がるモンスターたちの世界。彼らは夜な夜なドアを開いては子どもたちを怖がらせているのだが、実は彼らは“モンスターズ株式会社”のれっきとした社員なのだ。この会社は、モンスターシティの貴重なエネルギー源である子どもたちの悲鳴を集めるのがその仕事。しかし、最近の子どもは簡単には怖がってくれない。モンスターズ社の経営も苦しくなってきている。そんなある日、大事件が発生した。モンスターたちが実はもっとも怖れる人間の女の子がモンスターシティに紛れ込んでしまったのだ！');
insert into movie (title, genre, company, year, image, abst) values ('ファインディング・ニモ', 5, 7, 2003, 'movie20.jpg', '舞台はオーストラリアの美しい海。カクレクマノミのマーリンは、妻との間に生まれた卵の世話をしながら生活しており、目の前に迫った子どもたちの孵化を今や遅しと待ち構えていた。とても幸せな日々を送っていたマーリンだったが、毒を持つオニカマスに襲われてしまい、卵と妻は姿を消してしまった。たった一つだけ残った卵から生まれた子どもにニモと名付け、愛情たっぷりに育てた。ニモは過保護なマーリンにうんざりしながらも学校に通っていたが、漁をしていた人間に捕らわれてしまった。マーリンはニモを奪還すべく、ナンヨウハギのドリーと共に旅に出た……');
insert into movie (title, genre, company, year, image, abst) values ('天空の城ラピュタ', 5, 8, 1986, 'movie21.jpg', 'スウィフトの「ガリバー旅行記」をモチーフに、宮崎駿がオリジナル原案で描いた冒険アクション。空に浮かぶ伝説の島“ラピュタ”や反重力作用を持つ“飛行石”といったファンタジックなプロット、そして躍動感溢れるストーリー＆卓越した演出など、宮崎監督の手腕が冴える傑作娯楽活劇。スラッグ峡谷に住む見習い機械工のパズーはある日、空から降りてきた不思議な少女を助ける。その少女・シータは、浮力を持つ謎の鉱石“飛行石”を身につけていた。やがてパズーは、飛行石を狙う政府機関や海賊たちの陰謀に巻き込まれ、かつて地上を支配したという伝説の天空島「ラピュタ帝国」に誘われてゆく。');
insert into movie (title, genre, company, year, image, abst) values ('もののけ姫', 5, 8, 1997, 'movie22.jpg', '自然と人間の関係をテーマとし続けてきた宮崎駿の集大成的作品で、それまでの日本映画の歴代興行記録を塗り替える大ヒットとなった。山里に住む若者アシタカは、怒りと憎しみにより“タタリ神”と化した猪神から呪いをかけられてしまう。呪いを解く術を求めて旅に出るアシタカはやがて、西方の地で“タタラ”の村にたどり着く。エボシ御前が率いるその村では、鉄を造り続けていたが、同時にそれは神々の住む森を破壊することでもあった。そして、そんなタタラ達に戦いを挑むサンの存在をアシタカは知る。人の子でありながら山犬に育てられた彼女は“もののけ姫”と呼ばれていた……');
insert into movie (title, genre, company, year, image, abst) values ('思い出のマーニー', 5, 8, 2014, 'movie23.jpg', '心を閉ざした少女杏奈は、ぜんそくの療養を目的に親戚が生活している海沿いの村にやって来た。そんなある日、彼女の前に誰もいない屋敷の青い窓に閉じ込められた、きれいなブロンドの少女マーニーが姿を見せる。その出会い以来、杏奈の身の回りでは立て続けに奇妙な出来事が起きるようになるが、それは二人だけの秘密だった。 ');

insert into genre (name) values ('アクション');
insert into genre (name) values ('ドラマ');
insert into genre (name) values ('コメディ');
insert into genre (name) values ('SF');
insert into genre (name) values ('アニメ');
insert into genre (name) values ('ホラー');

insert into company (name) values ('パラマウント映画');
insert into company (name) values ('ワーナー・ブラザース');
insert into company (name) values ('20世紀フォックス');
insert into company (name) values ('タッチストーン・ピクチャーズ');
insert into company (name) values ('ユニバーサル・スタジオ');
insert into company (name) values ('ウォルト・ディズニー・ピクチャーズ');
insert into company (name) values ('ピクサー・アニメーション・スタジオ');
insert into company (name) values ('スタジオジブリ');



insert into review (m_id, comment, rank) values (1, '感動しました。かっこいい映画でした…！',3);
insert into review (m_id, comment, rank) values (1, '楽しかったです！もう一回見たいと思いました。DVD買います。',3);
insert into review (m_id, comment, rank) values (2, '本当に素晴らしい。私は刑務所に入ったことは無いし(ましてや無実の罪で)、ほとんどの人はそうだろう。だがこの映画を観たときに多くの人が共感を覚えると思う。それは職場や学校での束縛だとか、いじめだとか、理不尽な出来事。権力に押さえつけられ、嫌な奴に迫害されて。そういった鬱屈した感情を自分に照らし合わせるのだろう。そして、負けそうになりながらもたった一筋の光、希望を信じて決して諦めない。日常で辛い思いや悔しい思いをしながらも頑張って生きている人ほど、この映画は眩しく映ると思うし、この映画が沢山の人達に支持されていることが私にとっての光だったりするのだ。色々粗探しをしては批判する方もいるようで、それも分からなくはないのだが、この作品に関してはあまり意味の無いことのように思う',5);
insert into review (m_id, comment, rank) values (2, '自分が同じ境遇だったら、彼のように行動はできず、絶望しているはず…。この作品を見て勇気が湧きました！どんどんチャレンジしていこうという気持ちになれました',4);
insert into review (m_id, comment, rank) values (3, 'バットマン…！アメリカのヒーローですね…わたしはあんまり好みではなかったです。',1);
insert into review (m_id, comment, rank) values (3, 'バットマンを見て育ったといっても過言ではないくらい大好きです！',5);
insert into review (m_id, comment, rank) values (4, 'ストーリーは、主人公がいつも生活している世界はマトリックスという虚無の世界と言うことを伝えられ、実際の世界で戦っていくという話初めて見たとき、数式の羅列で表現されているマトリックスの世界観に観いっていました。やはり弾丸避けが格好いい',3);
insert into review (m_id, comment, rank) values (4, 'やっぱり１作目が最高、ストーリーも世界観も最高 。',5);
insert into review (m_id, comment, rank) values (4, 'あの弾丸よけるシーン！もぅ最高にかっこよくて、その後、足を撃たれるところが最高にかっこわるい。現実世界はいろいろと不便なんですね。何が現実かわからないけど。そんな映画でした。',4);
insert into review (m_id, comment, rank) values (5, '公開されたときに映画館に見に行って今でもDVDでみています。ハリーが入学するまでのダイアゴン横丁やハーマイオニーとロンと会うところなどの魔法の世界へのわくわく感がとても楽しいです。',3);
insert into review (m_id, comment, rank) values (5, '印象的だったのが、クラス分けのシーン。グリフィンドールでもスリザリンでも、新入生が1人、また1人と決まっていくたびに、上級生はわーっと喜ぶ。みんな温かいなぁ。',5);
insert into review (m_id, comment, rank) values (6, 'ウンパルンパ大好きです。中毒です。',5);
insert into review (m_id, comment, rank) values (6, '小さい頃に観た印象ではウォンカやウンパルンパはとても不気味だ、といった感じでしたが、最近見返すと、家族の愛に溢れた映画でした。ワガママな女の子や、大食いの男の子など、個性的なキャラクターだらけで楽しく鑑賞できました。',4);
insert into review (m_id, comment, rank) values (7, '笑えてはらはらして大好きです！こどもを家に置いていきたくないですね…',4);
insert into review (m_id, comment, rank) values (8, '難しいです…ダースベーダーかっこいい',2);
insert into review (m_id, comment, rank) values (9, '夜の博物館で繰り広げられる愉快な物語です。気分が明るくなるような映画です♪',5);
insert into review (m_id, comment, rank) values (9, '自分が普段ダメな男なので、こういう父子の描き方、大好きです。自分の息子にも、こんなかっこいいところを見せたいな。',4);
insert into review (m_id, comment, rank) values (10, '最初は「最悪！！なんでわたしがこんな目に」なはずが気づけば「最高！！ここがわたしの居場所」に。これぞ神のみ加護',5);
insert into review (m_id, comment, rank) values (10, '聖歌隊の歌がとても幸せになった。マフィア？が信心深すぎるシーンでずっと笑った。宗教ってすごいなってなる作品。',3);
insert into review (m_id, comment, rank) values (11, '内容はわかりやすく、展開も読めるのですが、それでも、ぐいぐい引き込む力を含んだ内容、音声、映像等最後は感動で終われる映画。お勧めだけど、みんなきっと見てるはずｗｗｗ',5);
insert into review (m_id, comment, rank) values (11, '父娘(おやこ)の葛藤や愛を描いた点は評価できると思います。そして、なによりも、ラストに流れるエアロスミスが歌う「ミス・ア・シング」が最高！この曲の素晴らしさだけで成り立っているのではと思うほど、心に残ります。',5);
insert into review (m_id, comment, rank) values (12, 'もう４０年前の映画なのに、なんでこんなにもドキドキハラハラさせられるんだろう？要は今観てもまったく色褪せてない。それに、あのジョン・ウィリアムズの曲を聞くと、子供の頃、友達とプールでジョーズごっこをしたことを思い出した。そんな懐かしい記憶も思い出させてくれる映画って数少ないと思う。 ',4);
insert into review (m_id, comment, rank) values (13, '最高。その一言に尽きる。恐竜の習性がおかしいとか言われているが、それはしょうがない。古生物についての研究は日々進歩しているのだから',4);
insert into review (m_id, comment, rank) values (13, '恐竜怖かった',2);
insert into review (m_id, comment, rank) values (13, 'こどもが怖がって布団から出てきません',1);
insert into review (m_id, comment, rank) values (13, 'スリルを感じながら楽しんでみれました！楽しかったです^^',4);
insert into review (m_id, comment, rank) values (14, '感動の嵐！！！！',5);
insert into review (m_id, comment, rank) values (14, '涙が出てしまってスクリーンが霞みました',5);
insert into review (m_id, comment, rank) values (14, 'ETやりたい！！！',5);
insert into review (m_id, comment, rank) values (14, '未知の生物と遭遇するという非現実的な体験をわたしもしたいと思いました。',5);
insert into review (m_id, comment, rank) values (15, '舞台は未来のサンフランシスコだが、ほとんど日本みたい。ディズニーの王道で意外性は少なく、ある意味、安心して観ることができる。',5);
insert into review (m_id, comment, rank) values (16, '「育ての親をあっさりと見捨てすぎではないか」との意見をインターネット上で見掛けたが、それは不毛な議論しか生まないものである。この映画はグリム童話を題材にしており、善と悪は明示され、後者は排除されなければならない。（童話において登場人物は、善は白色、悪は黒色を纏っているそうだ。実際に、この映画にもそれは表れている。',5);
insert into review (m_id, comment, rank) values (17, '劇場の３D版が素晴らしかったです。',4);
insert into review (m_id, comment, rank) values (17, '映像が綺麗で何回も見てしまいました*',4);
insert into review (m_id, comment, rank) values (17, 'オラフかわいい',3);
insert into review (m_id, comment, rank) values (17, 'スヴェンってなんのためにいたのかわからなかった。',3);
insert into review (m_id, comment, rank) values (17, '松たか子の歌唱力すごかった',5);
insert into review (m_id, comment, rank) values (18, '映像が古かったが、よかったと思います',5);
insert into review (m_id, comment, rank) values (19, 'いやぁ本当に好き！！この時はピクサーの存在がよく分からず食わず嫌いだったのですがテレビで初めて見て、楽しくて、ブーちゃんが可愛い過ぎて、ドアの追いかけっこはハラハラ、ドキドキで、ブーちゃんの別れに思わず泣いちゃった…凄く好きな作品です♪悲鳴集めから笑い声集めに変わったのが素敵でした！',4);
insert into review (m_id, comment, rank) values (20, 'ストーリーは凄く良かったです。ニモが水槽から出ようとするシーンは「がんばれ!ニモ」と心のなかでいいました。親が必死で探すシーンもよかったです',5);
insert into review (m_id, comment, rank) values (20, 'マーリンがんばれ！',5);
insert into review (m_id, comment, rank) values (20, '親子愛が素晴らしかった〜;)',5);
insert into review (m_id, comment, rank) values (20, '泣きました！感動をありがとう(;O;)',5);
insert into review (m_id, comment, rank) values (21, 'バルス〜！！！',4);
insert into review (m_id, comment, rank) values (21, 'ラピュタは男のロマンが詰まっている！巨神兵やばいっす！',5);
insert into review (m_id, comment, rank) values (21, '運命の女の子降ってこーい！',3);
insert into review (m_id, comment, rank) values (21, 'ジブリで一番好き',5);
insert into review (m_id, comment, rank) values (22, '自然と人間の関係をテーマとし続けてきた宮崎駿の集大成的作品',4);
insert into review (m_id, comment, rank) values (22, 'アシタカかっこいい〜！ジブリで一番好きです〜',5);
insert into review (m_id, comment, rank) values (22, '中学生のわたしには難しかったです。',3);
insert into review (m_id, comment, rank) values (23, '難しかった',4);
insert into review (m_id, comment, rank) values (23, '話の流れが良くなかった、ジブリで期待してた分、残念',2);
