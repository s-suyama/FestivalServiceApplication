CREATE TABLE sponsors (
    sponsor_id INT NOT NULL COMMENT '主催者番号'
,   sponsor_name VARCHAR(50) NOT NULL COMMENT '主催者名'
,   representative_name VARCHAR(50) NOT NULL COMMENT '代表者氏名'
,   address VARCHAR(100) NOT NULL COMMENT '住所'
,   phone_number VARCHAR(20) NOT NULL COMMENT '電話番号'
,   email VARCHAR(100) NOT NULL COMMENT 'メールアドレス'
,   PRIMARY KEY (sponsor_id)
)
COMMENT = '主催者'
;

CREATE SEQUENCE sponsor_id
;

CREATE TABLE event_types(
    event_type_code CHAR(3) NOT NULL COMMENT '種目分類コード'
,   event_type_name VARCHAR(30) NOT NULL COMMENT '種目分類名'
,   PRIMARY KEY (event_type_code)
)
COMMENT = '種目分類'
;

CREATE TABLE events (
    event_code INT NOT NULL COMMENT '種目コード'
,   event_name VARCHAR(50) NOT NULL COMMENT '種目名'
,   event_type_code CHAR(3) NOT NULL COMMENT '種目分類コード'
,   PRIMARY KEY (event_code)
,   CONSTRAINT fk_events_event_type_code FOREIGN KEY (event_type_code) REFERENCES event_types (event_type_code)
)
COMMENT = '種目'
;

CREATE SEQUENCE event_code
;

CREATE TABLE conduct_services (
    conduct_service_code INT NOT NULL COMMENT '運営サービスコード'
,   conduct_service_name VARCHAR(50) NOT NULL COMMENT '運営サービス名'
,   charge_unit VARCHAR(30) NOT NULL COMMENT '課金単位'
,   unit_price DECIMAL(10, 2) NOT NULL COMMENT '単価'
,   PRIMARY KEY (conduct_service_code)
)
COMMENT = '運営サービス'
;

CREATE SEQUENCE conduct_service_code
;

CREATE TABLE items (
    item_code INT NOT NULL COMMENT 'アイテムコード'
,   item_name VARCHAR(30) NOT NULL COMMENT 'アイテム名'
,   PRIMARY KEY (item_code)
)
COMMENT = 'アイテム'
;

CREATE SEQUENCE item_code
;

CREATE TABLE festivals (
    festival_id INT NOT NULL COMMENT '大会番号'
,   festival_name VARCHAR(100) NOT NULL COMMENT '大会名'
,   sponsor_id BIGINT NOT NULL COMMENT '主催者番号'
,   held_date DATE NOT NULL COMMENT '開催年月日'
,   held_place_prefecture_code INT NOT NULL COMMENT '開催場所都道府県コード'
,   PRIMARY KEY (festival_id)
,   CONSTRAINT fk_festivals_sponsor_id FOREIGN KEY (sponsor_id) REFERENCES sponsors (sponsor_id)
)
COMMENT = '大会'
;

CREATE SEQUENCE festival_id
;

CREATE TABLE festivals_conduct_services (
    festival_id INT NOT NULL COMMENT '大会番号'
,   conduct_service_code INT NOT NULL COMMENT '運営サービスコード'
,   PRIMARY KEY (festival_id, conduct_service_code)
,   CONSTRAINT fk_festivals_conduct_services_festival_id FOREIGN KEY (festival_id) REFERENCES festivals (festival_id)
,   CONSTRAINT fk_festivals_conduct_services_conduct_service_code FOREIGN KEY (conduct_service_code) REFERENCES conduct_services (conduct_service_code)
)
COMMENT = '大会運営サービス'
;

CREATE TABLE festivals_items (
    festival_id INT NOT NULL COMMENT '大会番号'
,   item_code INT NOT NULL COMMENT 'アイテムコード'
,   PRIMARY KEY (festival_id, item_code)
,   CONSTRAINT fk_festivals_items_festival_id FOREIGN KEY (festival_id) REFERENCES festivals (festival_id)
,   CONSTRAINT fk_festivals_items_item_code FOREIGN KEY (item_code) REFERENCES items (item_code)
)
COMMENT = '大会アイテム'
;

CREATE TABLE entries (
    festival_id INT NOT NULL COMMENT '大会番号'
,   entry_id INT NOT NULL COMMENT 'エントリ枠番号'
,   entry_name VARCHAR(20) NOT NULL COMMENT 'エントリ枠名'
,   entry_description VARCHAR(100) NOT NULL COMMENT 'エントリ枠説明'
,   event_code INT NOT NULL COMMENT '種目コード'
,   capacity INT NOT NULL COMMENT '定員'
,   participation_fees DECIMAL(10,2) NOT NULL COMMENT '参加費用'
,   application_numbers INT NOT NULL COMMENT '参加申込数'
,   first_arrival_lottery_type ENUM('firstArrival', 'lottery') NOT NULL COMMENT '先着順抽選区分'
,   application_start_date DATE NOT NULL COMMENT '募集開始年月日'
,   application_end_date DATE NOT NULL COMMENT '募集終了年月日'
,   entry_status ENUM('beforeRecruitment', 'recruiting', 'underLottery', 'participantConfirmation') NOT NULL COMMENT 'エントリ枠状態'
,   PRIMARY KEY (festival_id, entry_id)
,   CONSTRAINT fk_entries_festival_id FOREIGN KEY (festival_id) REFERENCES festivals (festival_id)
)
COMMENT = 'エントリ枠'
;

CREATE SEQUENCE entry_id
;

CREATE TABLE lottery_entries (
    festival_id INT NOT NULL COMMENT '大会番号'
,   entry_id INT NOT NULL COMMENT 'エントリ枠番号'
,   lottery_date DATE NOT NULL COMMENT '抽選年月日'
,   following_entry_id INT COMMENT '後続エントリ枠番号'
,   PRIMARY KEY (festival_id, entry_id)
,   CONSTRAINT fk_lottery_entries_festival_id_entry_id FOREIGN KEY (festival_id, entry_id) REFERENCES entries (festival_id, entry_id)
,   CONSTRAINT fk_lottery_entries_festival_id_following_entry_id FOREIGN KEY (festival_id, following_entry_id) REFERENCES lottery_entries (festival_id, entry_id)
)
COMMENT = '抽選エントリ枠'
;

CREATE TABLE members (
    member_id INT NOT NULL COMMENT '会員番号'
,   member_name VARCHAR(40) NOT NULL COMMENT '会員氏名'
,   sex ENUM ('man', 'woman') NOT NULL COMMENT '性別'
,   birthday DATE NOT NULL COMMENT '生年月日'
,   address VARCHAR(100) NOT NULL COMMENT '住所'
,   phone_number VARCHAR(20) NOT NULL COMMENT '電話番号'
,   email VARCHAR(100) NOT NULL COMMENT 'メールアドレス'
,   PRIMARY KEY (member_id)
)
COMMENT = '会員'
;

CREATE SEQUENCE member_id
;

CREATE TABLE applications (
    festival_id INT NOT NULL COMMENT '大会番号'
,   member_id INT NOT NULL COMMENT '会員番号'
,   entry_id INT NOT NULL COMMENT 'エントリ枠番号'
,   application_date DATE NOT NULL COMMENT '参加申込年月日'
,   payment_date DATE COMMENT '入金年月日'
,   use_points DECIMAL(10, 2) NOT NULL COMMENT '使用ポイント'
,   PRIMARY KEY (festival_id, member_id)
,   CONSTRAINT fk_applications_festival_id FOREIGN KEY (festival_id) REFERENCES festivals (festival_id)
,   CONSTRAINT fk_applications_member_id FOREIGN KEY (member_id) REFERENCES members (member_id)
,   CONSTRAINT fk_applications_entry_id FOREIGN KEY (entry_id) REFERENCES entries (entry_id)
)
COMMENT = '参加申込み'
;

CREATE TABLE lottery_entry_results (
    festival_id INT NOT NULL COMMENT '大会番号'
,   member_id INT NOT NULL COMMENT '会員番号'
,   entry_id INT NOT NULL COMMENT 'エントリ枠番号'
,   lottery_result ENUM('winning', 'failed') NOT NULL COMMENT '抽選結果'
,   PRIMARY KEY (festival_id, member_id, entry_id)
,   CONSTRAINT fk_lottery_entry_results_festival_id_entry_id FOREIGN KEY (festival_id, entry_id) REFERENCES lottery_entries (festival_id, entry_id)
,   CONSTRAINT fk_lottery_entry_results_member_id FOREIGN KEY (member_id) REFERENCES members (member_id)
)
COMMENT = '抽選結果'
;

CREATE TABLE member_points (
    member_id INT NOT NULL COMMENT '会員番号'
,   balance DECIMAL(10,2) NOT NULL COMMENT 'ポイント残高'
,   PRIMARY KEY (member_id)
,   CONSTRAINT fk_member_points_member_id FOREIGN KEY (member_id) REFERENCES members (member_id)
)
COMMENT = '会員ポイント'
;
