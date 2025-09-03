-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS "public"."accounts";
CREATE TABLE "public"."accounts" (
  "id" bigserial NOT NULL,
  "name" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "last_ip_address" varchar(18) COLLATE "pg_catalog"."default" NOT NULL,
  "last_access" timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  "email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "permission" varchar(18) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'PLAYER'::character varying,
  "premium" bool DEFAULT false,
  "online" bool DEFAULT false
)
;

-- ----------------------------
-- Records of accounts
-- ----------------------------

-- ----------------------------
-- Table structure for card_templates
-- ----------------------------
DROP TABLE IF EXISTS "public"."card_templates";
CREATE TABLE "public"."card_templates" (
  "id" int8 NOT NULL,
  "name" varchar(16) COLLATE "pg_catalog"."default",
  "atk" int4 NOT NULL DEFAULT 0,
  "atk_type" varchar(1) COLLATE "pg_catalog"."default",
  "p_def" int4 NOT NULL DEFAULT 0,
  "m_def" int4 NOT NULL DEFAULT 0,
  "rate_lvl_atk" float4 NOT NULL DEFAULT 0,
  "rate_lvl_p_def" float4 NOT NULL DEFAULT 0,
  "rate_lvl_m_def" float4 NOT NULL DEFAULT 0,
  "rate_lvl_atk_type_to_x" float4 NOT NULL DEFAULT 0,
  "rate_lvl_atk_type_to_a" float4 NOT NULL DEFAULT 0,
  "type" varchar(16) NOT NULL COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of card_templates
-- ----------------------------
INSERT INTO "public"."card_templates" VALUES (51,'Abadon',7,'M',6,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (70,'Alexander',13,'M',11,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (67,'Ark',14,'M',5,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (40,'Hecteyes',5,'M',2,4,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (41,'Ash',5,'M',3,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (63,'Atomos',4,'M',6,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (66,'Bahamut',12,'M',8,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (8,'Bomb',1,'M',0,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (21,'Carrion Worm',2,'M',1,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (75,'Dark Matter',12,'M',3,12,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (74,'Elixir',6,'M',6,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (37,'Feather Circle',4,'M',2,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (68,'Fenrir',8,'M',2,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (4,'Flan',0,'M',0,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (59,'Flare',12,'M',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (43,'Gargoyle',5,'M',3,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (48,'Garuda',6,'M',4,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (25,'Gimme Cat',3,'M',1,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (45,'Grimlock',5,'M',2,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (56,'Hades',15,'M',12,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (38,'Hecteyes',4,'M',0,4,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (27,'Hedgehog Pie',3,'M',1,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (57,'Holy',8,'M',2,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (61,'Ifrit',6,'M',9,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (65,'Leviathan',11,'M',6,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (69,'Madeen',10,'M',1,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (49,'Malboro',5,'M',3,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (14,'Mandragora',1,'M',0,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (58,'Meteor',11,'M',10,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (12,'Mimic',1,'M',1,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (91,'Mog',1,'M',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'PET');
INSERT INTO "public"."card_templates" VALUES (50,'Mover',6,'M',15,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (98,'Namingway',7,'M',7,7,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (17,'Nymph',2,'M',0,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (64,'Odin',12,'M',8,4,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (55,'Ozma',13,'M',0,12,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (26,'Ragtimer',3,'M',2,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (62,'Ramuh',4,'M',1,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (76,'Ribbon',0,'M',12,15,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (60,'Shiva',5,'M',0,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPELL');
INSERT INTO "public"."card_templates" VALUES (35,'Tantarian',4,'M',2,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (96,'Twin Moons',6,'M',5,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (44,'Vepal',5,'M',3,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (47,'Veteran',5,'M',1,9,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (42,'Wraith',5,'M',4,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (13,'Wyerd',1,'M',0,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (11,'Yeti',1,'M',0,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (33,'Zemzelett',4,'M',1,5,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (7,'Zombie',1,'M',1,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (32,'Abomination',4,'P',3,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (100,'Airship',7,'M',7,7,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (94,'Alexandria',0,'P',11,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'TOWN');
INSERT INTO "public"."card_templates" VALUES (23,'Antlion',3,'P',2,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (52,'Behemot',11,'P',4,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (31,'Blazer Beetle',4,'P',5,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (81,'Blue Narciss',8,'P',8,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (99,'Boco',7,'M',7,7,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (24,'Cactuar',3,'P',12,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (84,'Cargo Ship',2,'P',6,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (22,'Cerberus',2,'P',2,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (89,'Chocobo',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'PET');
INSERT INTO "public"."card_templates" VALUES (15,'Crawler',2,'P',2,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (20,'Dragonfly',2,'P',2,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (71,'Excalibur II',15,'P',11,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (2,'Fang',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (90,'Fat Chocobo',1,'P',1,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'PET');
INSERT INTO "public"."card_templates" VALUES (92,'Frog',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'PET');
INSERT INTO "public"."card_templates" VALUES (97,'Gargant',2,'P',0,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (79,'Genji',6,'P',6,10,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (1,'Goblin',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (36,'Grand Dragon',4,'P',4,4,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (85,'Hilda Garde 1',6,'P',4,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (82,'Hilda Garde',2,'P',6,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (83,'Invincible',11,'P',8,12,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (9,'Ironite',1,'P',1,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (53,'Iron Man',12,'P',6,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (95,'Alexandria',0,'P',6,11,0.03125,0.03125,0.03125,0.015625,0.0078125,'TOWN');
INSERT INTO "public"."card_templates" VALUES (6,'Lizard Man',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (73,'Masamune',12,'P',11,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (80,'Mythrill Sword',1,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (54,'Nova Dragon',14,'P',7,12,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (29,'Ochu',3,'P',2,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (93,'Oglop',2,'P',1,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'PET');
INSERT INTO "public"."card_templates" VALUES (39,'Ogre',5,'P',4,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (87,'Prima Vista',1,'P',6,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (28,'Ralvuimago',3,'P',4,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (86,'Red Rose',8,'P',1,8,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (10,'Sahagin',1,'P',1,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (18,'Sand Golem',2,'P',2,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (16,'Sand Scorpion',2,'P',2,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (78,'Save The Queen',6,'P',3,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (3,'Skeleton',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (34,'Stroper',4,'P',3,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (77,'Tiger Racket',0,'P',0,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (46,'Tonberry',2,'P',3,3,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (30,'Ochu',3,'P',3,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');
INSERT INTO "public"."card_templates" VALUES (72,'Ultima Weapon',15,'P',1,6,0.03125,0.03125,0.03125,0.015625,0.0078125,'ITEM');
INSERT INTO "public"."card_templates" VALUES (88,'Viltgance',14,'P',8,1,0.03125,0.03125,0.03125,0.015625,0.0078125,'SHIP');
INSERT INTO "public"."card_templates" VALUES (5,'Zaghnol',0,'P',0,0,0.03125,0.03125,0.03125,0.015625,0.0078125,'SPECIAL');
INSERT INTO "public"."card_templates" VALUES (19,'Zuu',2,'P',0,2,0.03125,0.03125,0.03125,0.015625,0.0078125,'MONSTER');

-- ----------------------------
-- Table structure for cards
-- ----------------------------
DROP TABLE IF EXISTS "public"."cards";
CREATE TABLE "public"."cards" (
  "id" bigserial NOT NULL,
  "card_id" int8 NOT NULL,
  "profile_id" int8 NOT NULL,
  "atk_arrows" int4 NOT NULL,
  "atk" int4 DEFAULT 0,
  "atk_type" varchar(1) COLLATE "pg_catalog"."default",
  "p_def" int4 DEFAULT 0,
  "m_def" int4 DEFAULT 0,
  "rate_lvl_atk" float4 DEFAULT 0.32,
  "rate_lvl_p_def" float4 DEFAULT 0.32,
  "rate_lvl_m_def" float4 DEFAULT 0.32,
  "rate_lvl_atk_type_to_a" float4 DEFAULT 0.01,
  "rate_lvl_atk_type_to_x" float4 DEFAULT 0.026,
  "base" bool DEFAULT true,
  "valuable" int4 DEFAULT 0
)
;

-- ----------------------------
-- Records of cards
-- ----------------------------

-- ----------------------------
-- Table structure for profiles
-- ----------------------------
DROP TABLE IF EXISTS "public"."profiles";
CREATE TABLE "public"."profiles" (
  "id" bigserial NOT NULL,
  "portrait" varchar(18) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0,
  "gil" int8 NOT NULL DEFAULT 500,
  "talent_id" int8 NOT NULL DEFAULT 1,
  "wins" int4 DEFAULT 0,
  "losses" int4 DEFAULT 0,
  "rank" varchar(255) COLLATE "pg_catalog"."default" DEFAULT 0,
  "collector_rank" varchar(255) COLLATE "pg_catalog"."default",
  "rating" int4,
  "account_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of profiles
-- ----------------------------

-- ----------------------------
-- Table structure for talents
-- ----------------------------
DROP TABLE IF EXISTS "public"."talents";
CREATE TABLE "public"."talents" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "handler" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of talents
-- ----------------------------
INSERT INTO "public"."talents" VALUES (1, 'Lucky 7', 'Увеличивает шансы на рост ваших карт после победы, а также шанса, что вы будете ходить первым при броске монеты.', 'com.finfan.server.talents.HandlerLucky7');
INSERT INTO "public"."talents" VALUES (2, 'Phoenix Down!', 'Один раз за поединок, в карточной дуэли, ваша карта не будет обращено во вражеску. Но когда это произойдет?...', 'com.finfan.server.talents.PhoenixDown');

-- ----------------------------
-- Indexes structure for table accounts
-- ----------------------------
CREATE UNIQUE INDEX "index_accounts_name" ON "public"."accounts" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table accounts
-- ----------------------------
ALTER TABLE "public"."accounts" ADD CONSTRAINT "accounts_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table card_templates
-- ----------------------------
ALTER TABLE "public"."card_templates" ADD CONSTRAINT "card_templates_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cards
-- ----------------------------
ALTER TABLE "public"."cards" ADD CONSTRAINT "cards_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table profiles
-- ----------------------------
ALTER TABLE "public"."profiles" ADD CONSTRAINT "profiles_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table talents
-- ----------------------------
ALTER TABLE "public"."talents" ADD CONSTRAINT "unique_talents_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table talents
-- ----------------------------
ALTER TABLE "public"."talents" ADD CONSTRAINT "talents_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table cards
-- ----------------------------
ALTER TABLE "public"."cards" ADD CONSTRAINT "fk_cards_card_id__card_templates_id" FOREIGN KEY ("card_id") REFERENCES "public"."card_templates" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."cards" ADD CONSTRAINT "fk_cards_profile_id_profiles_id" FOREIGN KEY ("profile_id") REFERENCES "public"."profiles" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table profiles
-- ----------------------------
ALTER TABLE "public"."profiles" ADD CONSTRAINT "fk_profiles_account_id_accounts_id" FOREIGN KEY ("account_id") REFERENCES "public"."accounts" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."profiles" ADD CONSTRAINT "fk_profiles_talent_id_dict_talents_id" FOREIGN KEY ("talent_id") REFERENCES "public"."talents" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Table structure for shop_cards
-- ----------------------------
DROP TABLE IF EXISTS "public"."shop_cards";
CREATE TABLE "public"."shop_cards" (
  "id" bigserial NOT NULL,
  "card_id" int8 NOT NULL,
  "atk_arrows" int4 NOT NULL,
  "price" int4 NOT NULL
)
;

-- ----------------------------
-- Primary Key structure for table shop_cards
-- ----------------------------
ALTER TABLE "public"."shop_cards" ADD CONSTRAINT "shop_cards_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table shop_cards
-- ----------------------------
ALTER TABLE "public"."shop_cards" ADD CONSTRAINT "fk_shop_cards_card_id_card_templates_id" FOREIGN KEY ("card_id") REFERENCES "public"."card_templates" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Table structure for card_decks
-- ----------------------------
DROP TABLE IF EXISTS "public"."card_decks";
CREATE TABLE "public"."card_decks" (
  "id" bigserial NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "profile_id" int8 NOT NULL,
  "card1" int8 NOT NULL,
  "card2" int8 NOT NULL,
  "card3" int8 NOT NULL,
  "card4" int8 NOT NULL,
  "card5" int8 NOT NULL
)
;

-- ----------------------------
-- Primary Key structure for table card_decks
-- ----------------------------
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "card_decks_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table card_decks
-- ----------------------------
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_card1_id_cards_id" FOREIGN KEY ("card1") REFERENCES "public"."cards" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_card2_id_cards_id" FOREIGN KEY ("card2") REFERENCES "public"."cards" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_card3_id_cards_id" FOREIGN KEY ("card3") REFERENCES "public"."cards" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_card4_id_cards_id" FOREIGN KEY ("card4") REFERENCES "public"."cards" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_card5_id_cards_id" FOREIGN KEY ("card5") REFERENCES "public"."cards" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."card_decks" ADD CONSTRAINT "fk_profile_id_profiles_id" FOREIGN KEY ("profile_id") REFERENCES "public"."profiles" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
