--<ScriptOptions statementTerminator=";"/>

CREATE TABLE "MAILROOM"."THESAURUS_TERMS" (
		"THESAURUS_TERMS_ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 NO CYCLE CACHE 20),		
		"THESAURUS_LEVEL0" VARCHAR(300),
		"THESAURUS_LEVEL1" VARCHAR(300),
		"THESAURUS_LEVEL2" VARCHAR(300),
		"THESAURUS_LEVEL3" VARCHAR(300)
	)
	DATA CAPTURE NONE;

