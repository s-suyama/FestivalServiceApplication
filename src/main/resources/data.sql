INSERT INTO sponsors (
    sponsor_id
,   sponsor_name
,   representative_name
,   address
,   phone_number
,   email
)
VALUES (
    1
,   'XXX食品'
,   '田中 一郎'
,   '東京都渋谷区渋谷１－２－３'
,   '03-1234-5678'
,   'xxx@example.com'
)
,(
    2
,   'YYY電気'
,   '佐藤 二郎'
,   '神奈川県横浜市４－５－６'
,   '045-123-4567'
,   'yyy@example.com'
)
,(
    3
,   'ZZZ自動車'
,   '鈴木 三郎'
,   '千葉県千葉市７－８－９'
,   '043-123-4567'
,   'zzz@example.com'
)
;

ALTER SEQUENCE sponsor_id RESTART WITH 4
;

INSERT INTO event_types (
    event_type_code
,   event_type_name
)
VALUES (
    'R01'
,   'ランニング'
),
(
    'B01'
,   '自転車レース'
)
;

INSERT INTO events (
    event_code
,   event_name
,   event_type_code
)
VALUES (
    1
,   'フルマラソン'
,   'R01'
)
,(
    2
,   'ハーフマラソン'
,   'R01'
),(
    3
,   '自転車ロードレース'
,   'B01'
)
;

ALTER SEQUENCE event_code RESTART WITH 4
;

INSERT INTO conduct_services (
    conduct_service_code
,   conduct_service_name
,   charge_unit
,   unit_price
)
VALUES (
    1
,   '告知サービス'
,   '1大会あたり'
,   10000
)
,(
    2
,   'エントリサービス'
,   '１大会あたり'
,   20000
)
,(
    3
,   '記録計測サービス'
,   '１種目あたり'
,   5000
)
;

ALTER SEQUENCE conduct_service_code RESTART WITH 4
;

INSERT INTO items (
    item_code
,   item_name
)
VALUES (
    1
,   '参加賞'
),(
    2
,   'ナンバカード'
),(
    3
,   'ICタグ'
)
;

ALTER SEQUENCE item_code RESTART WITH 4
;

INSERT INTO festivals (
    festival_id
,   festival_name
,   sponsor_id
,   held_date
,   held_place_prefecture_code
)
VALUES (
    1
,   'スポーツ大会'
,   1
,   '2019-10-01'
,   13
)
;

ALTER SEQUENCE festival_id RESTART WITH 2
;

INSERT INTO festivals_conduct_services (
    festival_id
,   conduct_service_code
)
VALUES (
    1
,   1
), (
    1
,   2
), (
    1
,   3
)
;

INSERT INTO festivals_items (
    festival_id
,   item_code
)
VALUES (
    1
,   1
),(
    1
,   2
),(
    1
,   3
)
;

INSERT INTO entries (
    festival_id
,   entry_id
,   entry_name
,   entry_description
,   event_code
,   capacity
,   participation_fees
,   application_numbers
,   first_arrival_lottery_type
,   application_start_date
,   application_end_date
,   entry_status
)
VALUES (
    1
,   1
,   '一般枠'
,   '一般枠の説明です'
,   1
,   100
,   1000
,   0
,   'lottery'
,   '2019-07-01'
,   '2019-09-20'
,   'underLottery'
),(
    1
,   2
,   '地元優先枠'
,   '地元優先枠の説明です'
,   1
,   50
,   500
,   0
,   'firstArrival'
,   '2019-07-01'
,   '2019-09-20'
,   'recruiting'
)
;

ALTER SEQUENCE entry_id RESTART WITH 3
;

INSERT INTO lottery_entries (
    festival_id
,   entry_id
,   lottery_date
)
VALUES (
    1
,   1
,   '2019-09-21'
)
;

INSERT INTO members (
    member_id
,   member_name
,   sex
,   birthday
,   address
,   phone_number
,   email
)
VALUES (
    1
,   '江崎 博幸'
,   'man'
,   '1990-04-01'
,   '東京都渋谷区神宮前'
,   '090-1111-1111'
,   'ezaki@example.com'
),(
    2
,   '津久井 聡志'
,   'man'
,   '1990-05-01'
,   '東京都渋谷区渋谷'
,   '090-2222-2222'
,   'tsukui@example.com'
),(
    3
,   '堀内 みどり'
,   'woman'
,   '1990-06-01'
,   '東京都渋谷区恵比寿'
,   '090-3333-3333'
,   'horiuchi@example.com'
),(
    4
,   '下山 祥子'
,   'woman'
,   '1990-07-01'
,   '東京都渋谷区代々木'
,   '090-4444-4444'
,   'shimoyama@example.com'
)
;

ALTER SEQUENCE member_id RESTART WITH 5
;

INSERT INTO member_points (
    member_id
,   balance
)
VALUES (
    1
,   100
),(
    2
,   200
),(
    3
,   300
),(
    4
,   400
)
;
