# 大会運営システムサンプル
## 概要
IPA 平成31年度 春期データベーススペシャリスト試験 午後１問題　の問１の問題で出題された大会運営システムをドメイン駆動設計で作成してみたサンプルプログラムになります。
また、画面は存在せず、APIのみとなります。

[問題のPDFダウンロードページ](https://www.jitec.ipa.go.jp/1_04hanni_sukiru/mondai_kaitou_2019h31_1/2019h31h_db_pm1_qs.pdf)

## 注意事項
* 問題内では、システムを利用する人がA社、主催者、大会に参加申し込む会員の３パターン考えられますが、今回は利用者の区別は行っておりません。
なので、認証、認可の処理は作成していません。
* 抽選枠の申込終了後、抽選結果がバッチ処理等で登録されると思いますが、今回は、抽選結果を登録するAPIを作成しています。

## エントリポイント
以下の3つのエントリポイントを作成しました。

### エントリ枠に申し込む

URL : /applications/entry

HTTP METHOD : POST

POST DATA
* festivalId : 大会番号
* memberId : 会員番号
* entryId : エントリ枠番号
* applicationDate : 参加申込年月日

### 抽選結果を登録する

URL : /lottery-entry-result

HTTP METHOD : POST

POST DATA
* festivalId : 大会番号
* memberId : 会員番号
* entryId : エントリ枠番号
* lotteryResult : 抽選結果

### 入金する

URL : /applications/payment

HTTP METHOD : POST

POST DATA
* festivalId : 大会番号
* memberId : 会員番号
* paymentDate : 入金年月日
* usePoints : 使用ポイント