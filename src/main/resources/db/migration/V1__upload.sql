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
  "permission" varchar(18) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'PLAYER'::character varying,
  "premium" bool DEFAULT false
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
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "atk" int4,
  "atk_type" varchar(255) COLLATE "pg_catalog"."default",
  "p_def" int4,
  "m_def" int4,
  "rate_lvl_atk" float4,
  "rate_lvl_atk_type" float4,
  "rate_lvl_mdef" float4,
  "rate_lvl_pdef" float4
)
;

-- ----------------------------
-- Records of card_templates
-- ----------------------------
INSERT INTO "public"."card_templates" VALUES (15, 'Crawler', 2, 'P', 0, 2, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (20, 'Dragonfly', 2, 'P', 1, 2, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (2, 'Fang', 0, 'P', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (1, 'Goblin', 0, 'P', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (9, 'Ironite', 1, 'P', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (6, 'Lizard Man', 0, 'P', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (10, 'Sahagin', 1, 'P', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (18, 'Sand Golem', 2, 'P', 0, 2, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (16, 'Sand Scorpion', 2, 'P', 0, 2, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (3, 'Skeleton', 0, 'P', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (5, 'Zaghnol', 0, 'P', 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (19, 'Zuu', 2, 'P', 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (8, 'Bomb', 1, 'M', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (4, 'Flan', 0, 'M', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (14, 'Mandragora', 1, 'M', 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (12, 'Mimic', 1, 'M', 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (17, 'Nymph', 2, 'M', 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (13, 'Wyerd', 1, 'M', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (11, 'Yeti', 1, 'M', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO "public"."card_templates" VALUES (7, 'Zombie', 1, 'M', 0, 1, NULL, NULL, NULL, NULL);

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
  "rate_lvl_atk" float4 DEFAULT 0.48,
  "rate_lvl_atk_type" float4 DEFAULT 0.12,
  "rate_lvl_pdef" float4 DEFAULT 0.48,
  "rate_lvl_mdef" float4 DEFAULT 0.48,
  "base" bool DEFAULT true
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
