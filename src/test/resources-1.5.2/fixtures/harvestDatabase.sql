--
-- The contents of this file are subject to the license and copyright
-- detailed in the LICENSE and NOTICE files at the root of the source
-- tree and available online at
--
-- http://www.dspace.org/license/
--
--
-- PostgreSQL database dump
--




--
-- Name: getnextid(character varying); Type: FUNCTION; Schema: public; Owner: dspace
--

CREATE FUNCTION getnextid(character varying) RETURNS integer
    LANGUAGE sql
    AS $_$SELECT CAST (nextval($1 || '_seq') AS INTEGER) AS RESULT $_$;




--
-- Name: bi_2_dis; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_2_dis (
    id integer NOT NULL,
    value text,
    sort_value text
);


--
-- Name: bi_2_dis_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_2_dis_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_2_dis_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_2_dis_seq', 22, true);


--
-- Name: bi_2_dmap; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_2_dmap (
    map_id integer NOT NULL,
    item_id integer,
    distinct_id integer
);


--
-- Name: bi_2_dmap_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_2_dmap_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_2_dmap_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_2_dmap_seq', 22, true);


--
-- Name: bi_4_dis; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_4_dis (
    id integer NOT NULL,
    value text,
    sort_value text
);


--
-- Name: bi_4_dis_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_4_dis_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_4_dis_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_4_dis_seq', 29, true);


--
-- Name: bi_4_dmap; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_4_dmap (
    map_id integer NOT NULL,
    item_id integer,
    distinct_id integer
);


--
-- Name: bi_4_dmap_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_4_dmap_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_4_dmap_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_4_dmap_seq', 41, true);


--
-- Name: bi_item; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_item (
    id integer NOT NULL,
    item_id integer,
    sort_1 text,
    sort_2 text,
    sort_3 text
);


--
-- Name: bi_item_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_item_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_item_seq', 22, true);


--
-- Name: bi_withdrawn; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_withdrawn (
    id integer NOT NULL,
    item_id integer,
    sort_1 text,
    sort_2 text,
    sort_3 text
);


--
-- Name: bi_withdrawn_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bi_withdrawn_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bi_withdrawn_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bi_withdrawn_seq', 1, false);


--
-- Name: bitstream; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bitstream (
    bitstream_id integer NOT NULL,
    bitstream_format_id integer,
    name character varying(256),
    size_bytes bigint,
    checksum character varying(64),
    checksum_algorithm character varying(32),
    description text,
    user_format_description text,
    source character varying(256),
    internal_id character varying(256),
    deleted boolean,
    store_number integer,
    sequence_id integer
);


--
-- Name: bitstream_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bitstream_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bitstream_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bitstream_seq', 47, true);


--
-- Name: bitstreamformatregistry; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bitstreamformatregistry (
    bitstream_format_id integer NOT NULL,
    mimetype character varying(256),
    short_description character varying(128),
    description text,
    support_level integer,
    internal boolean
);


--
-- Name: bitstreamformatregistry_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bitstreamformatregistry_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bitstreamformatregistry_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bitstreamformatregistry_seq', 73, true);


--
-- Name: bundle; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bundle (
    bundle_id integer NOT NULL,
    name character varying(16),
    primary_bitstream_id integer
);


--
-- Name: bundle2bitstream; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bundle2bitstream (
    id integer NOT NULL,
    bundle_id integer,
    bitstream_id integer
);


--
-- Name: bundle2bitstream_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bundle2bitstream_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bundle2bitstream_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bundle2bitstream_seq', 47, true);


--
-- Name: bundle_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE bundle_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('bundle_seq', 44, true);


--
-- Name: checksum_history; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE checksum_history (
    check_id bigint NOT NULL,
    bitstream_id integer,
    process_start_date timestamp without time zone,
    process_end_date timestamp without time zone,
    checksum_expected character varying,
    checksum_calculated character varying,
    result character varying
);


--
-- Name: checksum_history_check_id_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE checksum_history_check_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: checksum_history_check_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dspace
--

ALTER SEQUENCE checksum_history_check_id_seq OWNED BY checksum_history.check_id;


--
-- Name: checksum_history_check_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('checksum_history_check_id_seq', 1, false);


--
-- Name: checksum_results; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE checksum_results (
    result_code character varying NOT NULL,
    result_description character varying
);


--
-- Name: collection; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE collection (
    collection_id integer NOT NULL,
    name character varying(128),
    short_description character varying(512),
    introductory_text text,
    logo_bitstream_id integer,
    template_item_id integer,
    provenance_description text,
    license text,
    copyright_text text,
    side_bar_text text,
    workflow_step_1 integer,
    workflow_step_2 integer,
    workflow_step_3 integer,
    submitter integer,
    admin integer
);


--
-- Name: collection2item; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE collection2item (
    id integer NOT NULL,
    collection_id integer,
    item_id integer
);


--
-- Name: collection2item_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE collection2item_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: collection2item_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('collection2item_seq', 23, true);


--
-- Name: collection_item_count; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE collection_item_count (
    collection_id integer NOT NULL,
    count integer
);


--
-- Name: collection_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE collection_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: collection_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('collection_seq', 8, true);


--
-- Name: communities2item; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE communities2item (
    id integer NOT NULL,
    community_id integer,
    item_id integer
);


--
-- Name: communities2item_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE communities2item_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: communities2item_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('communities2item_seq', 23, true);


--
-- Name: community; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE community (
    community_id integer NOT NULL,
    name character varying(128),
    short_description character varying(512),
    introductory_text text,
    logo_bitstream_id integer,
    copyright_text text,
    side_bar_text text
);


--
-- Name: community2collection; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE community2collection (
    id integer NOT NULL,
    community_id integer,
    collection_id integer
);


--
-- Name: community2collection_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE community2collection_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: community2collection_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('community2collection_seq', 8, true);


--
-- Name: community2community; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE community2community (
    id integer NOT NULL,
    parent_comm_id integer,
    child_comm_id integer
);


--
-- Name: community2community_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE community2community_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: community2community_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('community2community_seq', 1, false);


--
-- Name: community2item; Type: VIEW; Schema: public; Owner: dspace
--

CREATE VIEW community2item AS
    SELECT community2collection.community_id, collection2item.item_id FROM community2collection, collection2item WHERE (collection2item.collection_id = community2collection.collection_id);


--
-- Name: community_item_count; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE community_item_count (
    community_id integer NOT NULL,
    count integer
);


--
-- Name: community_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE community_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: community_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('community_seq', 3, true);


--
-- Name: dctyperegistry_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE dctyperegistry_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: dctyperegistry_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('dctyperegistry_seq', 1, false);


--
-- Name: metadatafieldregistry_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE metadatafieldregistry_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: metadatafieldregistry_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('metadatafieldregistry_seq', 71, true);


--
-- Name: metadatafieldregistry; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE metadatafieldregistry (
    metadata_field_id integer DEFAULT nextval('metadatafieldregistry_seq'::regclass) NOT NULL,
    metadata_schema_id integer NOT NULL,
    element character varying(64),
    qualifier character varying(64),
    scope_note text
);


--
-- Name: metadatavalue_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE metadatavalue_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: metadatavalue_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('metadatavalue_seq', 260, true);


--
-- Name: metadatavalue; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE metadatavalue (
    metadata_value_id integer DEFAULT nextval('metadatavalue_seq'::regclass) NOT NULL,
    item_id integer,
    metadata_field_id integer,
    text_value text,
    text_lang character varying(24),
    place integer
);


--
-- Name: dcvalue; Type: VIEW; Schema: public; Owner: dspace
--

CREATE VIEW dcvalue AS
    SELECT metadatavalue.metadata_value_id AS dc_value_id, metadatavalue.item_id, metadatavalue.metadata_field_id AS dc_type_id, metadatavalue.text_value, metadatavalue.text_lang, metadatavalue.place FROM metadatavalue, metadatafieldregistry WHERE ((metadatavalue.metadata_field_id = metadatafieldregistry.metadata_field_id) AND (metadatafieldregistry.metadata_schema_id = 1));


--
-- Name: dcvalue_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE dcvalue_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: dcvalue_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('dcvalue_seq', 1, false);


--
-- Name: eperson; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE eperson (
    eperson_id integer NOT NULL,
    email character varying(64),
    password character varying(64),
    firstname character varying(64),
    lastname character varying(64),
    can_log_in boolean,
    require_certificate boolean,
    self_registered boolean,
    last_active timestamp without time zone,
    sub_frequency integer,
    phone character varying(32),
    netid character varying(64),
    language character varying(64)
);


--
-- Name: eperson_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE eperson_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: eperson_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('eperson_seq', 1, true);


--
-- Name: epersongroup; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE epersongroup (
    eperson_group_id integer NOT NULL,
    name character varying(256)
);


--
-- Name: epersongroup2eperson; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE epersongroup2eperson (
    id integer NOT NULL,
    eperson_group_id integer,
    eperson_id integer
);


--
-- Name: epersongroup2eperson_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE epersongroup2eperson_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: epersongroup2eperson_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('epersongroup2eperson_seq', 1, true);


--
-- Name: epersongroup2workspaceitem_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE epersongroup2workspaceitem_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: epersongroup2workspaceitem_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('epersongroup2workspaceitem_seq', 1, false);


--
-- Name: epersongroup2workspaceitem; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE epersongroup2workspaceitem (
    id integer DEFAULT nextval('epersongroup2workspaceitem_seq'::regclass) NOT NULL,
    eperson_group_id integer,
    workspace_item_id integer
);


--
-- Name: epersongroup_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE epersongroup_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: epersongroup_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('epersongroup_seq', 8, true);


--
-- Name: fileextension; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE fileextension (
    file_extension_id integer NOT NULL,
    bitstream_format_id integer,
    extension character varying(16)
);


--
-- Name: fileextension_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE fileextension_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: fileextension_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('fileextension_seq', 89, true);


--
-- Name: group2group; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE group2group (
    id integer NOT NULL,
    parent_id integer,
    child_id integer
);


--
-- Name: group2group_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE group2group_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: group2group_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('group2group_seq', 1, false);


--
-- Name: group2groupcache; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE group2groupcache (
    id integer NOT NULL,
    parent_id integer,
    child_id integer
);


--
-- Name: group2groupcache_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE group2groupcache_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: group2groupcache_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('group2groupcache_seq', 1, false);


--
-- Name: handle; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE handle (
    handle_id integer NOT NULL,
    handle character varying(256),
    resource_type_id integer,
    resource_id integer
);


--
-- Name: handle_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE handle_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: handle_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('handle_seq', 33, true);


--
-- Name: history_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE history_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: history_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('history_seq', 1, false);


--
-- Name: historystate_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE historystate_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: historystate_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('historystate_seq', 1, false);


--
-- Name: item; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE item (
    item_id integer NOT NULL,
    submitter_id integer,
    in_archive boolean,
    withdrawn boolean,
    last_modified timestamp with time zone,
    owning_collection integer
);


--
-- Name: item2bundle; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE item2bundle (
    id integer NOT NULL,
    item_id integer,
    bundle_id integer
);


--
-- Name: item2bundle_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE item2bundle_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: item2bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('item2bundle_seq', 44, true);


--
-- Name: item_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE item_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: item_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('item_seq', 24, true);


--
-- Name: metadataschemaregistry_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE metadataschemaregistry_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: metadataschemaregistry_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('metadataschemaregistry_seq', 1, true);


--
-- Name: metadataschemaregistry; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE metadataschemaregistry (
    metadata_schema_id integer DEFAULT nextval('metadataschemaregistry_seq'::regclass) NOT NULL,
    namespace character varying(256),
    short_id character varying(32)
);


--
-- Name: most_recent_checksum; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE most_recent_checksum (
    bitstream_id integer NOT NULL,
    to_be_processed boolean NOT NULL,
    expected_checksum character varying NOT NULL,
    current_checksum character varying NOT NULL,
    last_process_start_date timestamp without time zone NOT NULL,
    last_process_end_date timestamp without time zone NOT NULL,
    checksum_algorithm character varying NOT NULL,
    matched_prev_checksum boolean NOT NULL,
    result character varying
);


--
-- Name: registrationdata; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE registrationdata (
    registrationdata_id integer NOT NULL,
    email character varying(64),
    token character varying(48),
    expires timestamp without time zone
);


--
-- Name: registrationdata_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE registrationdata_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: registrationdata_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('registrationdata_seq', 1, false);


--
-- Name: resourcepolicy; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE resourcepolicy (
    policy_id integer NOT NULL,
    resource_type_id integer,
    resource_id integer,
    action_id integer,
    eperson_id integer,
    epersongroup_id integer,
    start_date date,
    end_date date
);


--
-- Name: resourcepolicy_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE resourcepolicy_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: resourcepolicy_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('resourcepolicy_seq', 607, true);


--
-- Name: subscription; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE subscription (
    subscription_id integer NOT NULL,
    eperson_id integer,
    collection_id integer
);


--
-- Name: subscription_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE subscription_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: subscription_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('subscription_seq', 1, false);


--
-- Name: tasklistitem; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE tasklistitem (
    tasklist_id integer NOT NULL,
    eperson_id integer,
    workflow_id integer
);


--
-- Name: tasklistitem_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE tasklistitem_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: tasklistitem_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('tasklistitem_seq', 1, false);


--
-- Name: workflowitem; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE workflowitem (
    workflow_id integer NOT NULL,
    item_id integer,
    collection_id integer,
    state integer,
    owner integer,
    multiple_titles boolean,
    published_before boolean,
    multiple_files boolean
);


--
-- Name: workflowitem_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE workflowitem_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: workflowitem_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('workflowitem_seq', 22, true);


--
-- Name: workspaceitem; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE workspaceitem (
    workspace_item_id integer NOT NULL,
    item_id integer,
    collection_id integer,
    multiple_titles boolean,
    published_before boolean,
    multiple_files boolean,
    stage_reached integer,
    page_reached integer
);


--
-- Name: workspaceitem_seq; Type: SEQUENCE; Schema: public; Owner: dspace
--

CREATE SEQUENCE workspaceitem_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: workspaceitem_seq; Type: SEQUENCE SET; Schema: public; Owner: dspace
--

SELECT pg_catalog.setval('workspaceitem_seq', 24, true);


--
-- Name: check_id; Type: DEFAULT; Schema: public; Owner: dspace
--

ALTER TABLE checksum_history ALTER COLUMN check_id SET DEFAULT nextval('checksum_history_check_id_seq'::regclass);


--
-- Data for Name: bi_2_dis; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bi_2_dis VALUES (1, 'smith, fred', 'smith, fred');
INSERT INTO bi_2_dis VALUES (2, 'rooney, wayne', 'rooney, wayne');
INSERT INTO bi_2_dis VALUES (3, 'brown', 'brown');
INSERT INTO bi_2_dis VALUES (4, 'burns, paul', 'burns, paul');
INSERT INTO bi_2_dis VALUES (5, 'sinatra, frank', 'sinatra, frank');
INSERT INTO bi_2_dis VALUES (6, 'springer, jerry', 'springer, jerry');
INSERT INTO bi_2_dis VALUES (7, 'Mercury, Freddie', 'mercury, freddie');
INSERT INTO bi_2_dis VALUES (8, 'Senna, Ayrton', 'senna, ayrton');
INSERT INTO bi_2_dis VALUES (9, 'Zuck, Mark', 'zuck, mark');
INSERT INTO bi_2_dis VALUES (10, 'Tutor, Hibernate', 'tutor, hibernate');
INSERT INTO bi_2_dis VALUES (11, 'Sauce, Tartare', 'sauce, tartare');
INSERT INTO bi_2_dis VALUES (12, 'Lopez, Maria', 'lopez, maria');
INSERT INTO bi_2_dis VALUES (13, 'Jones, Indiana', 'jones, indiana');
INSERT INTO bi_2_dis VALUES (14, 'Falcon, Millenium', 'falcon, millenium');
INSERT INTO bi_2_dis VALUES (15, 'Harper, Charlie', 'harper, charlie');
INSERT INTO bi_2_dis VALUES (16, 'Bolt, Usain', 'bolt, usain');
INSERT INTO bi_2_dis VALUES (17, 'Sugar, Alan', 'sugar, alan');
INSERT INTO bi_2_dis VALUES (18, 'Beck, Martin', 'beck, martin');
INSERT INTO bi_2_dis VALUES (19, 'Fowler, Martin', 'fowler, martin');
INSERT INTO bi_2_dis VALUES (20, 'Beck, Kent', 'beck, kent');
INSERT INTO bi_2_dis VALUES (21, 'Davies, Rachel', 'davies, rachel');
INSERT INTO bi_2_dis VALUES (22, 'Zhao, Lau', 'zhao, lau');


--
-- Data for Name: bi_2_dmap; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bi_2_dmap VALUES (1, 1, 1);
INSERT INTO bi_2_dmap VALUES (2, 2, 2);
INSERT INTO bi_2_dmap VALUES (3, 3, 3);
INSERT INTO bi_2_dmap VALUES (4, 4, 4);
INSERT INTO bi_2_dmap VALUES (5, 5, 5);
INSERT INTO bi_2_dmap VALUES (6, 6, 6);
INSERT INTO bi_2_dmap VALUES (7, 7, 7);
INSERT INTO bi_2_dmap VALUES (8, 8, 8);
INSERT INTO bi_2_dmap VALUES (9, 9, 9);
INSERT INTO bi_2_dmap VALUES (10, 10, 10);
INSERT INTO bi_2_dmap VALUES (11, 11, 11);
INSERT INTO bi_2_dmap VALUES (12, 12, 12);
INSERT INTO bi_2_dmap VALUES (13, 13, 13);
INSERT INTO bi_2_dmap VALUES (14, 14, 14);
INSERT INTO bi_2_dmap VALUES (15, 16, 15);
INSERT INTO bi_2_dmap VALUES (16, 17, 16);
INSERT INTO bi_2_dmap VALUES (17, 18, 17);
INSERT INTO bi_2_dmap VALUES (18, 19, 18);
INSERT INTO bi_2_dmap VALUES (19, 21, 19);
INSERT INTO bi_2_dmap VALUES (20, 22, 20);
INSERT INTO bi_2_dmap VALUES (21, 23, 21);
INSERT INTO bi_2_dmap VALUES (22, 24, 22);


--
-- Data for Name: bi_4_dis; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bi_4_dis VALUES (1, 'ruby', 'ruby');
INSERT INTO bi_4_dis VALUES (2, 'rails', 'rails');
INSERT INTO bi_4_dis VALUES (3, 'action', 'action');
INSERT INTO bi_4_dis VALUES (4, 'book', 'book');
INSERT INTO bi_4_dis VALUES (5, 'another rails book', 'another rails book');
INSERT INTO bi_4_dis VALUES (6, 'sinatra', 'sinatra');
INSERT INTO bi_4_dis VALUES (7, 'lighweight rack web framework', 'lighweight rack web framework');
INSERT INTO bi_4_dis VALUES (8, 'dependency injection', 'dependency injection');
INSERT INTO bi_4_dis VALUES (9, 'inversion of control', 'inversion of control');
INSERT INTO bi_4_dis VALUES (10, 'spring', 'spring');
INSERT INTO bi_4_dis VALUES (11, 'tutorials and guides', 'tutorials and guides');
INSERT INTO bi_4_dis VALUES (12, 'ORM', 'orm');
INSERT INTO bi_4_dis VALUES (13, 'JDBC', 'jdbc');
INSERT INTO bi_4_dis VALUES (14, 'hibernate', 'hibernate');
INSERT INTO bi_4_dis VALUES (15, 'Tutorial', 'tutorial');
INSERT INTO bi_4_dis VALUES (16, 'SQL', 'sql');
INSERT INTO bi_4_dis VALUES (17, 'Driver', 'driver');
INSERT INTO bi_4_dis VALUES (18, 'Hibernate', 'hibernate');
INSERT INTO bi_4_dis VALUES (19, 'POM.xml', 'pom.xml');
INSERT INTO bi_4_dis VALUES (20, 'Maven', 'maven');
INSERT INTO bi_4_dis VALUES (21, 'dependency', 'dependency');
INSERT INTO bi_4_dis VALUES (22, 'SMC', 'smc');
INSERT INTO bi_4_dis VALUES (23, 'Scrum', 'scrum');
INSERT INTO bi_4_dis VALUES (24, 'Sprint', 'sprint');
INSERT INTO bi_4_dis VALUES (25, 'Product Backlog', 'product backlog');
INSERT INTO bi_4_dis VALUES (26, 'Pair Programming', 'pair programming');
INSERT INTO bi_4_dis VALUES (27, 'Test Driven Development', 'test driven development');
INSERT INTO bi_4_dis VALUES (28, 'XP', 'xp');
INSERT INTO bi_4_dis VALUES (29, 'Waterfall', 'waterfall');


--
-- Data for Name: bi_4_dmap; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bi_4_dmap VALUES (1, 1, 1);
INSERT INTO bi_4_dmap VALUES (2, 1, 2);
INSERT INTO bi_4_dmap VALUES (3, 2, 3);
INSERT INTO bi_4_dmap VALUES (4, 2, 4);
INSERT INTO bi_4_dmap VALUES (5, 4, 5);
INSERT INTO bi_4_dmap VALUES (6, 5, 6);
INSERT INTO bi_4_dmap VALUES (7, 5, 7);
INSERT INTO bi_4_dmap VALUES (8, 6, 8);
INSERT INTO bi_4_dmap VALUES (9, 6, 9);
INSERT INTO bi_4_dmap VALUES (10, 7, 10);
INSERT INTO bi_4_dmap VALUES (11, 7, 11);
INSERT INTO bi_4_dmap VALUES (12, 8, 12);
INSERT INTO bi_4_dmap VALUES (13, 9, 13);
INSERT INTO bi_4_dmap VALUES (14, 9, 14);
INSERT INTO bi_4_dmap VALUES (15, 10, 14);
INSERT INTO bi_4_dmap VALUES (16, 10, 15);
INSERT INTO bi_4_dmap VALUES (17, 11, 17);
INSERT INTO bi_4_dmap VALUES (18, 11, 16);
INSERT INTO bi_4_dmap VALUES (19, 11, 18);
INSERT INTO bi_4_dmap VALUES (20, 12, 19);
INSERT INTO bi_4_dmap VALUES (21, 12, 20);
INSERT INTO bi_4_dmap VALUES (22, 13, 21);
INSERT INTO bi_4_dmap VALUES (23, 13, 20);
INSERT INTO bi_4_dmap VALUES (24, 14, 20);
INSERT INTO bi_4_dmap VALUES (25, 14, 15);
INSERT INTO bi_4_dmap VALUES (26, 16, 23);
INSERT INTO bi_4_dmap VALUES (27, 16, 22);
INSERT INTO bi_4_dmap VALUES (28, 17, 23);
INSERT INTO bi_4_dmap VALUES (29, 17, 24);
INSERT INTO bi_4_dmap VALUES (30, 18, 23);
INSERT INTO bi_4_dmap VALUES (31, 18, 25);
INSERT INTO bi_4_dmap VALUES (32, 19, 23);
INSERT INTO bi_4_dmap VALUES (33, 19, 15);
INSERT INTO bi_4_dmap VALUES (34, 21, 23);
INSERT INTO bi_4_dmap VALUES (35, 21, 26);
INSERT INTO bi_4_dmap VALUES (36, 22, 27);
INSERT INTO bi_4_dmap VALUES (37, 22, 28);
INSERT INTO bi_4_dmap VALUES (38, 23, 28);
INSERT INTO bi_4_dmap VALUES (39, 23, 15);
INSERT INTO bi_4_dmap VALUES (40, 24, 29);
INSERT INTO bi_4_dmap VALUES (41, 24, 15);


--
-- Data for Name: bi_item; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bi_item VALUES (1, 1, 'rails guide', '2011-11-09T17:17:31Z', '2011-11-09T17:17:31Z');
INSERT INTO bi_item VALUES (2, 2, 'rails 3 in action', '2011-11-10T17:21:38Z', '2011-11-10T17:21:38Z');
INSERT INTO bi_item VALUES (3, 3, 'rails tutorial', '1976-04-24', '2011-11-10T17:25:45Z');
INSERT INTO bi_item VALUES (4, 4, 'agile web development with rails', '2011-11-08T17:26:48Z', '2011-11-08T17:26:48Z');
INSERT INTO bi_item VALUES (5, 5, 'sinatra tutorials by fankie', '2011-11-10T17:28:50Z', '2011-11-10T17:28:50Z');
INSERT INTO bi_item VALUES (6, 6, 'ioc and di', '2011-11-10T17:31:36Z', '2011-11-10T17:31:36Z');
INSERT INTO bi_item VALUES (7, 7, 'guides and tutorials', '2011-11-10T17:33:57Z', '2011-11-10T17:33:57Z');
INSERT INTO bi_item VALUES (8, 8, 'omg its orm!!!', '2011-11-09T17:37:50Z', '2011-11-09T17:37:50Z');
INSERT INTO bi_item VALUES (9, 9, 'jdbc zuck''s', '2011-11-10T17:42:56Z', '2011-11-10T17:42:56Z');
INSERT INTO bi_item VALUES (10, 10, 'hibernate tutorials', '2011-11-10T17:45:18Z', '2011-11-10T17:45:18Z');
INSERT INTO bi_item VALUES (11, 11, 'sql and driver', '2011-11-08T17:48:42Z', '2011-11-08T17:48:42Z');
INSERT INTO bi_item VALUES (12, 12, 'check out my pom', '2011-11-10T17:52:03Z', '2011-11-10T17:52:03Z');
INSERT INTO bi_item VALUES (13, 13, 'dependencies guide', '2011-11-10T17:53:24Z', '2011-11-10T17:53:24Z');
INSERT INTO bi_item VALUES (14, 14, 'maven tutorials', '2011-11-10T17:55:54Z', '2011-11-10T17:55:54Z');
INSERT INTO bi_item VALUES (15, 16, 'scrum master certification guide', '2011-11-10T18:01:00Z', '2011-11-10T18:01:00Z');
INSERT INTO bi_item VALUES (16, 17, 'jamaican sprints', '2011-11-10T18:03:55Z', '2011-11-10T18:03:55Z');
INSERT INTO bi_item VALUES (17, 18, 'product backlog book', '2011-11-10T18:05:40Z', '2011-11-10T18:05:40Z');
INSERT INTO bi_item VALUES (18, 19, 'scrum tutorials', '2011-11-10T18:07:42Z', '2011-11-10T18:07:42Z');
INSERT INTO bi_item VALUES (19, 21, 'programming in pairs', '2011-11-10T18:17:22Z', '2011-11-10T18:17:22Z');
INSERT INTO bi_item VALUES (20, 22, 'tdd by example', '2011-11-10T18:19:47Z', '2011-11-10T18:19:47Z');
INSERT INTO bi_item VALUES (21, 23, 'xp tutorial', '2011-11-10T18:20:43Z', '2011-11-10T18:20:43Z');
INSERT INTO bi_item VALUES (22, 24, 'waterfall model tutorial', '2011-11-10T18:22:53Z', '2011-11-10T18:22:53Z');


--
-- Data for Name: bi_withdrawn; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: bitstream; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bitstream VALUES (11, 5, 'ioc.txt', 8, '671c8bf2d92e1fdd7f809da10f007399', 'MD5', 'ioc tutorial', NULL, '${resource.test.base}/upload/ioc.txt', '112600012558710613142296168518605670614', false, 0, 1);
INSERT INTO bitstream VALUES (1, 5, 'rails_guides.txt', 17, '03395dc638b0566daed65002bb061cf9', 'MD5', '', NULL, '${resource.test.base}/upload/rails_guides.txt', '53335323966931188202644889602841164165', false, 0, 1);
INSERT INTO bitstream VALUES (43, 2, 'license.txt', 89, '914863a833d5c94e7d917667d785c672', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '17483024113602878201267119692560286390', false, 0, 2);
INSERT INTO bitstream VALUES (2, 2, 'license.txt', 89, 'ea652d6b706a66b53541ee0d4a152b9b', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '89502048530481847461800880515293950328', false, 0, 2);
INSERT INTO bitstream VALUES (42, 5, 'tdd.txt', 8, '36f6aa3d1c0da471007c99684d73468b', 'MD5', NULL, NULL, '${resource.test.base}/upload/tdd.txt', '61133929249654463675307579201969680399', false, 0, 1);
INSERT INTO bitstream VALUES (3, 5, 'r3ia.txt', 9, '189e58744a3ffb0524bd81619800df90', 'MD5', '', NULL, '${resource.test.base}/upload/r3ia.txt', '29514415170061356452820001025325953591', false, 0, 1);
INSERT INTO bitstream VALUES (12, 5, 'di.txt', 7, '24c6cbbfd4215b77f96aaa8b11072b7c', 'MD5', 'dependancy injection tutorial', NULL, '${resource.test.base}/upload/di.txt', '160764368352028345213548661457107407102', false, 0, 2);
INSERT INTO bitstream VALUES (4, 2, 'license.txt', 89, 'ef19d6f7045e3a292e0e21d124ebc89c', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '81529803142074201490331457009050709043', false, 0, 2);
INSERT INTO bitstream VALUES (18, 2, 'license.txt', 89, 'c32fc7c3bc6f7ebc9a6004e29caf8eee', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '147722426091814997152625867590058691425', false, 0, 2);
INSERT INTO bitstream VALUES (5, 5, 'rails_tutorial.txt', 19, 'ff21fdbe9c1b7972f43411ce7cd9e80a', 'MD5', NULL, NULL, '${resource.test.base}/upload/rails_tutorial.txt', '138281908827175028135795355765461985888', false, 0, 1);
INSERT INTO bitstream VALUES (13, 2, 'license.txt', 89, 'f626df773be781de11d74180f4a651b8', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '75805568578582923813430086830611292891', false, 0, 3);
INSERT INTO bitstream VALUES (6, 2, 'license.txt', 89, '8385114e4ec55b9ec60bc9fe2ae84c45', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '133309393643313102435176272417415043144', false, 0, 2);
INSERT INTO bitstream VALUES (7, 5, 'awdwr.txt', 10, '01ef867ced6e7b0c8966211650adeee5', 'MD5', NULL, NULL, '${resource.test.base}/upload/awdwr.txt', '20868496207634499659770347946891995668', false, 0, 1);
INSERT INTO bitstream VALUES (23, 5, 'sql.txt', 8, '71f47ea290e55742c5992996e49fa1f3', 'MD5', 'sql guide', NULL, '${resource.test.base}/upload/sql.txt', '145207893832371879323865129356082018505', false, 0, 1);
INSERT INTO bitstream VALUES (8, 2, 'license.txt', 89, 'c63c5610e7bde735c10718468b9e897d', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '108037535157579524505706124216145505200', false, 0, 2);
INSERT INTO bitstream VALUES (14, 5, 'spring_guides.txt', 18, '18f2f8ae03cf0129490c592c4424bc04', 'MD5', 'spring guides', NULL, '${resource.test.base}/upload/spring_guides.txt', '60437859869354595305062922018839205011', false, 0, 1);
INSERT INTO bitstream VALUES (9, 5, 'sinatra_tutorial.txt', 21, '862fa51008cec90d681fbb291c56abd1', 'MD5', NULL, NULL, '${resource.test.base}/upload/sinatra_tutorial.txt', '2967859931292626174973016196720403293', false, 0, 1);
INSERT INTO bitstream VALUES (10, 2, 'license.txt', 89, 'e998da9ef60a874027c7318a9253c0ff', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '36750287864974188068233740044044574870', false, 0, 2);
INSERT INTO bitstream VALUES (19, 5, 'jdbc.txt', 9, '73d08e075748d40cf205eaa490941600', 'MD5', NULL, NULL, '${resource.test.base}/upload/jdbc.txt', '118344155101873722002784005941956507948', false, 0, 1);
INSERT INTO bitstream VALUES (15, 5, 'spring_tutorial.txt', 20, '9d1be970259d826ed105a1c2740798d6', 'MD5', 'spring tutorials', NULL, '${resource.test.base}/upload/spring_tutorial.txt', '49889710710271671909467380667569185660', false, 0, 2);
INSERT INTO bitstream VALUES (26, 5, 'pom.txt', 8, 'f96725ffe5336c8bbda7d3fef04a076b', 'MD5', NULL, NULL, '${resource.test.base}/upload/pom.txt', '11206995318093957473609492661774982358', false, 0, 1);
INSERT INTO bitstream VALUES (16, 2, 'license.txt', 89, 'ff2c19acd8857de50c919a440225b52c', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '140571464968246138827626446859177491452', false, 0, 3);
INSERT INTO bitstream VALUES (20, 2, 'license.txt', 89, 'b517840267b82d9d34e9f117ca804380', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '28680044869965188534976635796733330389', false, 0, 2);
INSERT INTO bitstream VALUES (17, 5, 'orm.txt', 8, '4e6ad61b7c95e6f1926f837422619850', 'MD5', NULL, NULL, '${resource.test.base}/upload/orm.txt', '89509523818845928606350299012807876577', false, 0, 1);
INSERT INTO bitstream VALUES (21, 5, 'hibernate-tutorial.txt', 23, '3be099dec9392a81a4a5bac0fa888c7b', 'MD5', NULL, NULL, '${resource.test.base}/upload/hibernate-tutorial.txt', '1104279638353041540181586609636675084', false, 0, 1);
INSERT INTO bitstream VALUES (24, 5, 'driver.txt', 11, '68b4f1665cba6fb5ec05ddd81326006e', 'MD5', 'Driver tutorial', NULL, '${resource.test.base}/upload/driver.txt', '114323537251147214165484765259613487164', false, 0, 2);
INSERT INTO bitstream VALUES (22, 2, 'license.txt', 89, 'f32932ed22bf8925d764c4513dd001d9', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '134462976002188128865306526563259497491', false, 0, 2);
INSERT INTO bitstream VALUES (27, 2, 'license.txt', 89, '3a330fe2c43c499804100c5a3ab8603a', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '32979199017927292516740758082087121113', false, 0, 2);
INSERT INTO bitstream VALUES (25, 2, 'license.txt', 89, '4198d0d8e0da2b50b2ccc12b68341263', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '129825466872490992678441844306788919830', false, 0, 3);
INSERT INTO bitstream VALUES (29, 2, 'license.txt', 89, '7d0733b067bc7068bdf0c1300809fcd8', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '88201028352253952646662110369016231811', false, 0, 2);
INSERT INTO bitstream VALUES (28, 5, 'dependancies.txt', 17, 'c6a71481dfcb588c10a82b07fbb4b6e0', 'MD5', NULL, NULL, '${resource.test.base}/upload/dependancies.txt', '168372933090381925596374193356836080562', false, 0, 1);
INSERT INTO bitstream VALUES (32, 5, 'smc.txt', 8, '99773f426a678c374b2e455affb11638', 'MD5', NULL, NULL, '${resource.test.base}/upload/smc.txt', '58340135240602239106182353341494431417', false, 0, 1);
INSERT INTO bitstream VALUES (30, 5, 'maven_tutorial.txt', 19, 'f7291aa12944442814e2843782a12191', 'MD5', NULL, NULL, '${resource.test.base}/upload/maven_tutorial.txt', '67319193820106218996352546289160072162', false, 0, 1);
INSERT INTO bitstream VALUES (31, 2, 'license.txt', 89, 'e9ba2ce5a846844288d947921faee78d', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '24314871343812910612294467785490450309', false, 0, 2);
INSERT INTO bitstream VALUES (33, 2, 'license.txt', 89, '28f16b7cb54c2c107d4c6ea7cf79f4d7', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '33986321611759981294669683693010798887', false, 0, 2);
INSERT INTO bitstream VALUES (35, 2, 'license.txt', 89, 'c92bff9d7f1a477e477b80383d3ac608', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '12700783383136381149876499770581737669', false, 0, 2);
INSERT INTO bitstream VALUES (34, 5, 'sprints.txt', 12, '4ba73ddd9107ad708acbbba27f3bcd4a', 'MD5', NULL, NULL, '${resource.test.base}/upload/sprints.txt', '121677795110155297933608382511910217412', false, 0, 1);
INSERT INTO bitstream VALUES (36, 5, 'product_backlog.txt', 20, 'cd7df045cf63366dd3aeb60487e17548', 'MD5', NULL, NULL, '${resource.test.base}/upload/product_backlog.txt', '113767714121502711545915067139820389896', false, 0, 1);
INSERT INTO bitstream VALUES (44, 5, 'xp_tutorial.txt', 16, 'e5325f646ecf2ac18e0177e1797abebf', 'MD5', NULL, NULL, '${resource.test.base}/upload/xp_tutorial.txt', '67815385587789000135625544094435966154', false, 0, 1);
INSERT INTO bitstream VALUES (37, 2, 'license.txt', 89, '841ef09db8f80d8b6ac468e6b95447b1', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '56698088363133075172170493510971561524', false, 0, 2);
INSERT INTO bitstream VALUES (45, 2, 'license.txt', 89, '7d9925ae6c353acf986220810f56d29a', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '66824207585407125719397932092736548443', false, 0, 2);
INSERT INTO bitstream VALUES (46, 5, 'waterfall_tutorial.txt', 23, '5af3a08e06ae717b8ed9defdafd56e19', 'MD5', NULL, NULL, '${resource.test.base}/upload/waterfall_tutorial.txt', '20407168772905563775631590546051105532', false, 0, 1);
INSERT INTO bitstream VALUES (38, 5, 'scrum_tutorial.txt', 19, 'eb756583f982101e713c5d416e912fe0', 'MD5', NULL, NULL, '${resource.test.base}/upload/scrum_tutorial.txt', '132946386885825103477706698235836013550', false, 0, 1);
INSERT INTO bitstream VALUES (47, 2, 'license.txt', 89, '3f5d648ce24429db382af14102cf7052', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '113762015175346178827481684414266704243', false, 0, 2);
INSERT INTO bitstream VALUES (39, 2, 'license.txt', 89, 'cb9266e5f2b9a73e57b881ea1c7250f1', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '39321748090820268357970424826874887717', false, 0, 2);
INSERT INTO bitstream VALUES (40, 5, 'pair_programming.txt', 21, 'cafa6e6c9caf9bd7ce4158833cabe3cd', 'MD5', NULL, NULL, '${resource.test.base}/upload/pair_programming.txt', '84684303297524678949607737652342275450', false, 0, 1);
INSERT INTO bitstream VALUES (41, 2, 'license.txt', 89, 'fc81aa90b0d879faedca7843b37b3314', 'MD5', NULL, NULL, 'Written by org.dspace.content.Item', '123495730367363095647705086514930481481', false, 0, 2);


--
-- Data for Name: bitstreamformatregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bitstreamformatregistry VALUES (1, 'application/octet-stream', 'Unknown', 'Unknown data format', 0, false);
INSERT INTO bitstreamformatregistry VALUES (2, 'text/plain; charset=utf-8', 'License', 'Item-specific license agreed upon to submission', 1, true);
INSERT INTO bitstreamformatregistry VALUES (3, 'application/pdf', 'Adobe PDF', 'Adobe Portable Document Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (4, 'text/xml', 'XML', 'Extensible Markup Language', 1, false);
INSERT INTO bitstreamformatregistry VALUES (5, 'text/plain', 'Text', 'Plain Text', 1, false);
INSERT INTO bitstreamformatregistry VALUES (6, 'text/html', 'HTML', 'Hypertext Markup Language', 1, false);
INSERT INTO bitstreamformatregistry VALUES (7, 'text/css', 'CSS', 'Cascading Style Sheets', 1, false);
INSERT INTO bitstreamformatregistry VALUES (8, 'application/msword', 'Microsoft Word', 'Microsoft Word', 1, false);
INSERT INTO bitstreamformatregistry VALUES (9, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Microsoft Word XML', 'Microsoft Word XML', 1, false);
INSERT INTO bitstreamformatregistry VALUES (10, 'application/vnd.ms-powerpoint', 'Microsoft Powerpoint', 'Microsoft Powerpoint', 1, false);
INSERT INTO bitstreamformatregistry VALUES (11, 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 'Microsoft Powerpoint XML', 'Microsoft Powerpoint XML', 1, false);
INSERT INTO bitstreamformatregistry VALUES (12, 'application/vnd.ms-excel', 'Microsoft Excel', 'Microsoft Excel', 1, false);
INSERT INTO bitstreamformatregistry VALUES (13, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'Microsoft Excel XML', 'Microsoft Excel XML', 1, false);
INSERT INTO bitstreamformatregistry VALUES (14, 'application/marc', 'MARC', 'Machine-Readable Cataloging records', 1, false);
INSERT INTO bitstreamformatregistry VALUES (15, 'image/jpeg', 'JPEG', 'Joint Photographic Experts Group/JPEG File Interchange Format (JFIF)', 1, false);
INSERT INTO bitstreamformatregistry VALUES (16, 'image/gif', 'GIF', 'Graphics Interchange Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (17, 'image/png', 'image/png', 'Portable Network Graphics', 1, false);
INSERT INTO bitstreamformatregistry VALUES (18, 'image/tiff', 'TIFF', 'Tag Image File Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (19, 'audio/x-aiff', 'AIFF', 'Audio Interchange File Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (20, 'audio/basic', 'audio/basic', 'Basic Audio', 1, false);
INSERT INTO bitstreamformatregistry VALUES (21, 'audio/x-wav', 'WAV', 'Broadcase Wave Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (22, 'video/mpeg', 'MPEG', 'Moving Picture Experts Group', 1, false);
INSERT INTO bitstreamformatregistry VALUES (23, 'text/richtext', 'RTF', 'Rich Text Format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (24, 'application/vnd.visio', 'Microsoft Visio', 'Microsoft Visio', 1, false);
INSERT INTO bitstreamformatregistry VALUES (25, 'application/x-filemaker', 'FMP3', 'Filemaker Pro', 1, false);
INSERT INTO bitstreamformatregistry VALUES (26, 'image/x-ms-bmp', 'BMP', 'Microsoft Windows bitmap', 1, false);
INSERT INTO bitstreamformatregistry VALUES (27, 'application/x-photoshop', 'Photoshop', 'Photoshop', 1, false);
INSERT INTO bitstreamformatregistry VALUES (28, 'application/postscript', 'Postscript', 'Postscript Files', 1, false);
INSERT INTO bitstreamformatregistry VALUES (29, 'video/quicktime', 'Video Quicktime', 'Video Quicktime', 1, false);
INSERT INTO bitstreamformatregistry VALUES (30, 'audio/x-mpeg', 'MPEG Audio', 'MPEG Audio', 1, false);
INSERT INTO bitstreamformatregistry VALUES (31, 'application/vnd.ms-project', 'Microsoft Project', 'Microsoft Project', 1, false);
INSERT INTO bitstreamformatregistry VALUES (32, 'application/mathematica', 'Mathematica', 'Mathematica Notebook', 1, false);
INSERT INTO bitstreamformatregistry VALUES (33, 'application/x-latex', 'LateX', 'LaTeX document', 1, false);
INSERT INTO bitstreamformatregistry VALUES (34, 'application/x-tex', 'TeX', 'Tex/LateX document', 1, false);
INSERT INTO bitstreamformatregistry VALUES (35, 'application/x-dvi', 'TeX dvi', 'TeX dvi format', 1, false);
INSERT INTO bitstreamformatregistry VALUES (36, 'application/sgml', 'SGML', 'SGML application (RFC 1874)', 1, false);
INSERT INTO bitstreamformatregistry VALUES (37, 'application/wordperfect5.1', 'WordPerfect', 'WordPerfect 5.1 document', 1, false);
INSERT INTO bitstreamformatregistry VALUES (38, 'audio/x-pn-realaudio', 'RealAudio', 'RealAudio file', 1, false);
INSERT INTO bitstreamformatregistry VALUES (39, 'image/x-photo-cd', 'Photo CD', 'Kodak Photo CD image', 1, false);
INSERT INTO bitstreamformatregistry VALUES (40, 'application/vnd.oasis.opendocument.text', 'OpenDocument Text', 'OpenDocument Text', 1, false);
INSERT INTO bitstreamformatregistry VALUES (41, 'application/vnd.oasis.opendocument.text-template', 'OpenDocument Text Template', 'OpenDocument Text Template', 1, false);
INSERT INTO bitstreamformatregistry VALUES (42, 'application/vnd.oasis.opendocument.text-web', 'OpenDocument HTML Template', 'OpenDocument HTML Template', 1, false);
INSERT INTO bitstreamformatregistry VALUES (43, 'application/vnd.oasis.opendocument.text-master', 'OpenDocument Master Document', 'OpenDocument Master Document', 1, false);
INSERT INTO bitstreamformatregistry VALUES (44, 'application/vnd.oasis.opendocument.graphics', 'OpenDocument Drawing', 'OpenDocument Drawing', 1, false);
INSERT INTO bitstreamformatregistry VALUES (45, 'application/vnd.oasis.opendocument.graphics-template', 'OpenDocument Drawing Template', 'OpenDocument Drawing Template', 1, false);
INSERT INTO bitstreamformatregistry VALUES (46, 'application/vnd.oasis.opendocument.presentation', 'OpenDocument Presentation', 'OpenDocument Presentation', 1, false);
INSERT INTO bitstreamformatregistry VALUES (47, 'application/vnd.oasis.opendocument.presentation-template', 'OpenDocument Presentation Template', 'OpenDocument Presentation Template', 1, false);
INSERT INTO bitstreamformatregistry VALUES (48, 'application/vnd.oasis.opendocument.spreadsheet', 'OpenDocument Spreadsheet', 'OpenDocument Spreadsheet', 1, false);
INSERT INTO bitstreamformatregistry VALUES (49, 'application/vnd.oasis.opendocument.spreadsheet-template', 'OpenDocument Spreadsheet Template', 'OpenDocument Spreadsheet Template', 1, false);
INSERT INTO bitstreamformatregistry VALUES (50, 'application/vnd.oasis.opendocument.chart', 'OpenDocument Chart', 'OpenDocument Chart', 1, false);
INSERT INTO bitstreamformatregistry VALUES (51, 'application/vnd.oasis.opendocument.formula', 'OpenDocument Formula', 'OpenDocument Formula', 1, false);
INSERT INTO bitstreamformatregistry VALUES (52, 'application/vnd.oasis.opendocument.database', 'OpenDocument Database', 'OpenDocument Database', 1, false);
INSERT INTO bitstreamformatregistry VALUES (53, 'application/vnd.oasis.opendocument.image', 'OpenDocument Image', 'OpenDocument Image', 1, false);
INSERT INTO bitstreamformatregistry VALUES (54, 'application/vnd.openofficeorg.extension', 'OpenOffice.org extension', 'OpenOffice.org extension (since OOo 2.1)', 1, false);
INSERT INTO bitstreamformatregistry VALUES (55, 'application/vnd.sun.xml.writer', 'Writer 6.0 documents', 'Writer 6.0 documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (56, 'application/vnd.sun.xml.writer.template', 'Writer 6.0 templates', 'Writer 6.0 templates', 1, false);
INSERT INTO bitstreamformatregistry VALUES (57, 'application/vnd.sun.xml.calc', 'Calc 6.0 spreadsheets', 'Calc 6.0 spreadsheets', 1, false);
INSERT INTO bitstreamformatregistry VALUES (58, 'application/vnd.sun.xml.calc.template', 'Calc 6.0 templates', 'Calc 6.0 templates', 1, false);
INSERT INTO bitstreamformatregistry VALUES (59, 'application/vnd.sun.xml.draw', 'Draw 6.0 documents', 'Draw 6.0 documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (60, 'application/vnd.sun.xml.draw.template', 'Draw 6.0 templates', 'Draw 6.0 templates', 1, false);
INSERT INTO bitstreamformatregistry VALUES (61, 'application/vnd.sun.xml.impress', 'Impress 6.0 presentations', 'Impress 6.0 presentations', 1, false);
INSERT INTO bitstreamformatregistry VALUES (62, 'application/vnd.sun.xml.impress.template', 'Impress 6.0 templates', 'Impress 6.0 templates', 1, false);
INSERT INTO bitstreamformatregistry VALUES (63, 'application/vnd.sun.xml.writer.global', 'Writer 6.0 global documents', 'Writer 6.0 global documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (64, 'application/vnd.sun.xml.math', 'Math 6.0 documents', 'Math 6.0 documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (65, 'application/vnd.stardivision.writer', 'StarWriter 5.x documents', 'StarWriter 5.x documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (66, 'application/vnd.stardivision.writer-global', 'StarWriter 5.x global documents', 'StarWriter 5.x global documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (67, 'application/vnd.stardivision.calc', 'StarCalc 5.x spreadsheets', 'StarCalc 5.x spreadsheets', 1, false);
INSERT INTO bitstreamformatregistry VALUES (68, 'application/vnd.stardivision.draw', 'StarDraw 5.x documents', 'StarDraw 5.x documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (69, 'application/vnd.stardivision.impress', 'StarImpress 5.x presentations', 'StarImpress 5.x presentations', 1, false);
INSERT INTO bitstreamformatregistry VALUES (70, 'application/vnd.stardivision.impress-packed', 'StarImpress Packed 5.x files', 'StarImpress Packed 5.x files', 1, false);
INSERT INTO bitstreamformatregistry VALUES (71, 'application/vnd.stardivision.math', 'StarMath 5.x documents', 'StarMath 5.x documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (72, 'application/vnd.stardivision.chart', 'StarChart 5.x documents', 'StarChart 5.x documents', 1, false);
INSERT INTO bitstreamformatregistry VALUES (73, 'application/vnd.stardivision.mail', 'StarMail 5.x mail files', 'StarMail 5.x mail files', 1, false);


--
-- Data for Name: bundle; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bundle VALUES (1, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (2, 'LICENSE', NULL);
INSERT INTO bundle VALUES (3, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (4, 'LICENSE', NULL);
INSERT INTO bundle VALUES (5, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (6, 'LICENSE', NULL);
INSERT INTO bundle VALUES (7, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (8, 'LICENSE', NULL);
INSERT INTO bundle VALUES (9, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (10, 'LICENSE', NULL);
INSERT INTO bundle VALUES (11, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (12, 'LICENSE', NULL);
INSERT INTO bundle VALUES (13, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (14, 'LICENSE', NULL);
INSERT INTO bundle VALUES (15, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (16, 'LICENSE', NULL);
INSERT INTO bundle VALUES (17, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (18, 'LICENSE', NULL);
INSERT INTO bundle VALUES (19, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (20, 'LICENSE', NULL);
INSERT INTO bundle VALUES (21, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (22, 'LICENSE', NULL);
INSERT INTO bundle VALUES (23, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (24, 'LICENSE', NULL);
INSERT INTO bundle VALUES (25, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (26, 'LICENSE', NULL);
INSERT INTO bundle VALUES (27, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (28, 'LICENSE', NULL);
INSERT INTO bundle VALUES (29, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (30, 'LICENSE', NULL);
INSERT INTO bundle VALUES (31, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (32, 'LICENSE', NULL);
INSERT INTO bundle VALUES (33, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (34, 'LICENSE', NULL);
INSERT INTO bundle VALUES (35, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (36, 'LICENSE', NULL);
INSERT INTO bundle VALUES (37, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (38, 'LICENSE', NULL);
INSERT INTO bundle VALUES (39, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (40, 'LICENSE', NULL);
INSERT INTO bundle VALUES (41, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (42, 'LICENSE', NULL);
INSERT INTO bundle VALUES (43, 'ORIGINAL', NULL);
INSERT INTO bundle VALUES (44, 'LICENSE', NULL);


--
-- Data for Name: bundle2bitstream; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO bundle2bitstream VALUES (1, 1, 1);
INSERT INTO bundle2bitstream VALUES (2, 2, 2);
INSERT INTO bundle2bitstream VALUES (3, 3, 3);
INSERT INTO bundle2bitstream VALUES (4, 4, 4);
INSERT INTO bundle2bitstream VALUES (5, 5, 5);
INSERT INTO bundle2bitstream VALUES (6, 6, 6);
INSERT INTO bundle2bitstream VALUES (7, 7, 7);
INSERT INTO bundle2bitstream VALUES (8, 8, 8);
INSERT INTO bundle2bitstream VALUES (9, 9, 9);
INSERT INTO bundle2bitstream VALUES (10, 10, 10);
INSERT INTO bundle2bitstream VALUES (11, 11, 11);
INSERT INTO bundle2bitstream VALUES (12, 11, 12);
INSERT INTO bundle2bitstream VALUES (13, 12, 13);
INSERT INTO bundle2bitstream VALUES (14, 13, 14);
INSERT INTO bundle2bitstream VALUES (15, 13, 15);
INSERT INTO bundle2bitstream VALUES (16, 14, 16);
INSERT INTO bundle2bitstream VALUES (17, 15, 17);
INSERT INTO bundle2bitstream VALUES (18, 16, 18);
INSERT INTO bundle2bitstream VALUES (19, 17, 19);
INSERT INTO bundle2bitstream VALUES (20, 18, 20);
INSERT INTO bundle2bitstream VALUES (21, 19, 21);
INSERT INTO bundle2bitstream VALUES (22, 20, 22);
INSERT INTO bundle2bitstream VALUES (23, 21, 23);
INSERT INTO bundle2bitstream VALUES (24, 21, 24);
INSERT INTO bundle2bitstream VALUES (25, 22, 25);
INSERT INTO bundle2bitstream VALUES (26, 23, 26);
INSERT INTO bundle2bitstream VALUES (27, 24, 27);
INSERT INTO bundle2bitstream VALUES (28, 25, 28);
INSERT INTO bundle2bitstream VALUES (29, 26, 29);
INSERT INTO bundle2bitstream VALUES (30, 27, 30);
INSERT INTO bundle2bitstream VALUES (31, 28, 31);
INSERT INTO bundle2bitstream VALUES (32, 29, 32);
INSERT INTO bundle2bitstream VALUES (33, 30, 33);
INSERT INTO bundle2bitstream VALUES (34, 31, 34);
INSERT INTO bundle2bitstream VALUES (35, 32, 35);
INSERT INTO bundle2bitstream VALUES (36, 33, 36);
INSERT INTO bundle2bitstream VALUES (37, 34, 37);
INSERT INTO bundle2bitstream VALUES (38, 35, 38);
INSERT INTO bundle2bitstream VALUES (39, 36, 39);
INSERT INTO bundle2bitstream VALUES (40, 37, 40);
INSERT INTO bundle2bitstream VALUES (41, 38, 41);
INSERT INTO bundle2bitstream VALUES (42, 39, 42);
INSERT INTO bundle2bitstream VALUES (43, 40, 43);
INSERT INTO bundle2bitstream VALUES (44, 41, 44);
INSERT INTO bundle2bitstream VALUES (45, 42, 45);
INSERT INTO bundle2bitstream VALUES (46, 43, 46);
INSERT INTO bundle2bitstream VALUES (47, 44, 47);


--
-- Data for Name: checksum_history; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: checksum_results; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO checksum_results VALUES ('INVALID_HISTORY', 'Install of the cheksum checking code do not consider this history as valid');
INSERT INTO checksum_results VALUES ('BITSTREAM_NOT_FOUND', 'The bitstream could not be found');
INSERT INTO checksum_results VALUES ('CHECKSUM_MATCH', 'Current checksum matched previous checksum');
INSERT INTO checksum_results VALUES ('CHECKSUM_NO_MATCH', 'Current checksum does not match previous checksum');
INSERT INTO checksum_results VALUES ('CHECKSUM_PREV_NOT_FOUND', 'Previous checksum was not found: no comparison possible');
INSERT INTO checksum_results VALUES ('BITSTREAM_INFO_NOT_FOUND', 'Bitstream info not found');
INSERT INTO checksum_results VALUES ('CHECKSUM_ALGORITHM_INVALID', 'Invalid checksum algorithm');
INSERT INTO checksum_results VALUES ('BITSTREAM_NOT_PROCESSED', 'Bitstream marked to_be_processed=false');
INSERT INTO checksum_results VALUES ('BITSTREAM_MARKED_DELETED', 'Bitstream marked deleted in bitstream table');


--
-- Data for Name: collection; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO collection VALUES (1, 'Rails', 'Collection on Rails', 'Collection of items related to Ruby on Rails', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Ruby Community', 'Ruby on Rails iz awesome!', NULL, NULL, NULL, NULL, NULL);
INSERT INTO collection VALUES (2, 'Sinatra', 'Collection on Sinatra', 'Collection of items related to Sinatra', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Ruby Community', 'Sinatra iz lightweight awesome!', NULL, NULL, NULL, 2, NULL);
INSERT INTO collection VALUES (3, 'Spring', 'Collection on Spring', 'Collection of items related to Sinatra', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Java Community', 'Sinatra', NULL, NULL, NULL, 3, NULL);
INSERT INTO collection VALUES (4, 'Hibernate', 'Collection on Hibernate', 'Collection of items related to Hibernate', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Java Community', 'Hibernate', NULL, NULL, NULL, 4, NULL);
INSERT INTO collection VALUES (5, 'Maven', 'Collection on Maven', 'Collection of items related to Maven', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Java Community', 'Mave', NULL, NULL, NULL, 5, NULL);
INSERT INTO collection VALUES (6, 'Scrum', 'Collection on Scrum', 'Collection of items related to Scrum', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Agile Community', 'Scrum', NULL, NULL, NULL, 6, NULL);
INSERT INTO collection VALUES (7, 'XP', 'Collection on XP', 'Collection of items related to XP', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Agile Community', 'XP', NULL, NULL, NULL, 7, NULL);
INSERT INTO collection VALUES (8, 'Waterfall', 'Collection on Waterfall', 'Collection of items related to Waterfall', NULL, NULL, 'Internet', 'GPL', '(c) 2011 Agile Community', 'Waterfall doesn''t belong to Agile!!', NULL, NULL, NULL, 8, NULL);


--
-- Data for Name: collection2item; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO collection2item VALUES (1, 6, 1);
INSERT INTO collection2item VALUES (2, 1, 1);
INSERT INTO collection2item VALUES (3, 1, 2);
INSERT INTO collection2item VALUES (4, 1, 3);
INSERT INTO collection2item VALUES (5, 1, 4);
INSERT INTO collection2item VALUES (6, 2, 5);
INSERT INTO collection2item VALUES (7, 3, 6);
INSERT INTO collection2item VALUES (8, 3, 7);
INSERT INTO collection2item VALUES (9, 3, 8);
INSERT INTO collection2item VALUES (10, 4, 9);
INSERT INTO collection2item VALUES (11, 4, 10);
INSERT INTO collection2item VALUES (12, 4, 11);
INSERT INTO collection2item VALUES (13, 5, 12);
INSERT INTO collection2item VALUES (14, 5, 13);
INSERT INTO collection2item VALUES (15, 5, 14);
INSERT INTO collection2item VALUES (16, 6, 16);
INSERT INTO collection2item VALUES (17, 6, 17);
INSERT INTO collection2item VALUES (18, 6, 18);
INSERT INTO collection2item VALUES (19, 6, 19);
INSERT INTO collection2item VALUES (20, 7, 21);
INSERT INTO collection2item VALUES (21, 7, 22);
INSERT INTO collection2item VALUES (22, 7, 23);
INSERT INTO collection2item VALUES (23, 8, 24);


--
-- Data for Name: collection_item_count; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: communities2item; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO communities2item VALUES (1, 3, 1);
INSERT INTO communities2item VALUES (2, 1, 1);
INSERT INTO communities2item VALUES (3, 1, 2);
INSERT INTO communities2item VALUES (4, 1, 3);
INSERT INTO communities2item VALUES (5, 1, 4);
INSERT INTO communities2item VALUES (6, 1, 5);
INSERT INTO communities2item VALUES (7, 2, 6);
INSERT INTO communities2item VALUES (8, 2, 7);
INSERT INTO communities2item VALUES (9, 2, 8);
INSERT INTO communities2item VALUES (10, 2, 9);
INSERT INTO communities2item VALUES (11, 2, 10);
INSERT INTO communities2item VALUES (12, 2, 11);
INSERT INTO communities2item VALUES (13, 2, 12);
INSERT INTO communities2item VALUES (14, 2, 13);
INSERT INTO communities2item VALUES (15, 2, 14);
INSERT INTO communities2item VALUES (16, 3, 16);
INSERT INTO communities2item VALUES (17, 3, 17);
INSERT INTO communities2item VALUES (18, 3, 18);
INSERT INTO communities2item VALUES (19, 3, 19);
INSERT INTO communities2item VALUES (20, 3, 21);
INSERT INTO communities2item VALUES (21, 3, 22);
INSERT INTO communities2item VALUES (22, 3, 23);
INSERT INTO communities2item VALUES (23, 3, 24);


--
-- Data for Name: community; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO community VALUES (1, 'Ruby', 'The Ruby Community', 'Welcome to the Ruby Community!', NULL, 'Everything presented carries a GPL licence', 'Ruby iz awesome!');
INSERT INTO community VALUES (2, 'Java Community', 'The Java Community', 'Welcome to the Java Community', NULL, '(c) 2011 Java Community', 'JVC');
INSERT INTO community VALUES (3, 'Agile', 'The Agile Community', 'Welcome to the Agile Community', NULL, '(c) 2011 Agile Community', 'Agile iz awesome!');


--
-- Data for Name: community2collection; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO community2collection VALUES (1, 1, 1);
INSERT INTO community2collection VALUES (2, 1, 2);
INSERT INTO community2collection VALUES (3, 2, 3);
INSERT INTO community2collection VALUES (4, 2, 4);
INSERT INTO community2collection VALUES (5, 2, 5);
INSERT INTO community2collection VALUES (6, 3, 6);
INSERT INTO community2collection VALUES (7, 3, 7);
INSERT INTO community2collection VALUES (8, 3, 8);


--
-- Data for Name: community2community; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: community_item_count; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: eperson; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO eperson VALUES (1, 'dspace@example.com', 'd2a147310ed200c73a5978baebf60e19', 'dspace', 'dspace', true, false, false, NULL, NULL, NULL, NULL, 'en');


--
-- Data for Name: epersongroup; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO epersongroup VALUES (0, 'Anonymous');
INSERT INTO epersongroup VALUES (1, 'Administrator');
INSERT INTO epersongroup VALUES (2, 'COLLECTION_2_SUBMIT');
INSERT INTO epersongroup VALUES (3, 'COLLECTION_3_SUBMIT');
INSERT INTO epersongroup VALUES (4, 'COLLECTION_4_SUBMIT');
INSERT INTO epersongroup VALUES (5, 'COLLECTION_5_SUBMIT');
INSERT INTO epersongroup VALUES (6, 'COLLECTION_6_SUBMIT');
INSERT INTO epersongroup VALUES (7, 'COLLECTION_7_SUBMIT');
INSERT INTO epersongroup VALUES (8, 'COLLECTION_8_SUBMIT');


--
-- Data for Name: epersongroup2eperson; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO epersongroup2eperson VALUES (1, 1, 1);


--
-- Data for Name: epersongroup2workspaceitem; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: fileextension; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO fileextension VALUES (1, 3, 'pdf');
INSERT INTO fileextension VALUES (2, 4, 'xml');
INSERT INTO fileextension VALUES (3, 5, 'txt');
INSERT INTO fileextension VALUES (4, 5, 'asc');
INSERT INTO fileextension VALUES (5, 6, 'htm');
INSERT INTO fileextension VALUES (6, 6, 'html');
INSERT INTO fileextension VALUES (7, 7, 'css');
INSERT INTO fileextension VALUES (8, 8, 'doc');
INSERT INTO fileextension VALUES (9, 9, 'docx');
INSERT INTO fileextension VALUES (10, 10, 'ppt');
INSERT INTO fileextension VALUES (11, 11, 'pptx');
INSERT INTO fileextension VALUES (12, 12, 'xls');
INSERT INTO fileextension VALUES (13, 13, 'xlsx');
INSERT INTO fileextension VALUES (14, 15, 'jpeg');
INSERT INTO fileextension VALUES (15, 15, 'jpg');
INSERT INTO fileextension VALUES (16, 16, 'gif');
INSERT INTO fileextension VALUES (17, 17, 'png');
INSERT INTO fileextension VALUES (18, 18, 'tiff');
INSERT INTO fileextension VALUES (19, 18, 'tif');
INSERT INTO fileextension VALUES (20, 19, 'aiff');
INSERT INTO fileextension VALUES (21, 19, 'aif');
INSERT INTO fileextension VALUES (22, 19, 'aifc');
INSERT INTO fileextension VALUES (23, 20, 'au');
INSERT INTO fileextension VALUES (24, 20, 'snd');
INSERT INTO fileextension VALUES (25, 21, 'wav');
INSERT INTO fileextension VALUES (26, 22, 'mpeg');
INSERT INTO fileextension VALUES (27, 22, 'mpg');
INSERT INTO fileextension VALUES (28, 22, 'mpe');
INSERT INTO fileextension VALUES (29, 23, 'rtf');
INSERT INTO fileextension VALUES (30, 24, 'vsd');
INSERT INTO fileextension VALUES (31, 25, 'fm');
INSERT INTO fileextension VALUES (32, 26, 'bmp');
INSERT INTO fileextension VALUES (33, 27, 'psd');
INSERT INTO fileextension VALUES (34, 27, 'pdd');
INSERT INTO fileextension VALUES (35, 28, 'ps');
INSERT INTO fileextension VALUES (36, 28, 'eps');
INSERT INTO fileextension VALUES (37, 28, 'ai');
INSERT INTO fileextension VALUES (38, 29, 'mov');
INSERT INTO fileextension VALUES (39, 29, 'qt');
INSERT INTO fileextension VALUES (40, 30, 'mpa');
INSERT INTO fileextension VALUES (41, 30, 'abs');
INSERT INTO fileextension VALUES (42, 30, 'mpega');
INSERT INTO fileextension VALUES (43, 31, 'mpp');
INSERT INTO fileextension VALUES (44, 31, 'mpx');
INSERT INTO fileextension VALUES (45, 31, 'mpd');
INSERT INTO fileextension VALUES (46, 32, 'ma');
INSERT INTO fileextension VALUES (47, 33, 'latex');
INSERT INTO fileextension VALUES (48, 34, 'tex');
INSERT INTO fileextension VALUES (49, 35, 'dvi');
INSERT INTO fileextension VALUES (50, 36, 'sgm');
INSERT INTO fileextension VALUES (51, 36, 'sgml');
INSERT INTO fileextension VALUES (52, 37, 'wpd');
INSERT INTO fileextension VALUES (53, 38, 'ra');
INSERT INTO fileextension VALUES (54, 38, 'ram');
INSERT INTO fileextension VALUES (55, 39, 'pcd');
INSERT INTO fileextension VALUES (56, 40, 'odt');
INSERT INTO fileextension VALUES (57, 41, 'ott');
INSERT INTO fileextension VALUES (58, 42, 'oth');
INSERT INTO fileextension VALUES (59, 43, 'odm');
INSERT INTO fileextension VALUES (60, 44, 'odg');
INSERT INTO fileextension VALUES (61, 45, 'otg');
INSERT INTO fileextension VALUES (62, 46, 'odp');
INSERT INTO fileextension VALUES (63, 47, 'otp');
INSERT INTO fileextension VALUES (64, 48, 'ods');
INSERT INTO fileextension VALUES (65, 49, 'ots');
INSERT INTO fileextension VALUES (66, 50, 'odc');
INSERT INTO fileextension VALUES (67, 51, 'odf');
INSERT INTO fileextension VALUES (68, 52, 'odb');
INSERT INTO fileextension VALUES (69, 53, 'odi');
INSERT INTO fileextension VALUES (70, 54, 'oxt');
INSERT INTO fileextension VALUES (71, 55, 'sxw');
INSERT INTO fileextension VALUES (72, 56, 'stw');
INSERT INTO fileextension VALUES (73, 57, 'sxc');
INSERT INTO fileextension VALUES (74, 58, 'stc');
INSERT INTO fileextension VALUES (75, 59, 'sxd');
INSERT INTO fileextension VALUES (76, 60, 'std');
INSERT INTO fileextension VALUES (77, 61, 'sxi');
INSERT INTO fileextension VALUES (78, 62, 'sti');
INSERT INTO fileextension VALUES (79, 63, 'sxg');
INSERT INTO fileextension VALUES (80, 64, 'sxm');
INSERT INTO fileextension VALUES (81, 65, 'sdw');
INSERT INTO fileextension VALUES (82, 66, 'sgl');
INSERT INTO fileextension VALUES (83, 67, 'sdc');
INSERT INTO fileextension VALUES (84, 68, 'sda');
INSERT INTO fileextension VALUES (85, 69, 'sdd');
INSERT INTO fileextension VALUES (86, 70, 'sdp');
INSERT INTO fileextension VALUES (87, 71, 'smf');
INSERT INTO fileextension VALUES (88, 72, 'sds');
INSERT INTO fileextension VALUES (89, 73, 'sdm');


--
-- Data for Name: group2group; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: group2groupcache; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: handle; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO handle VALUES (1, '123456789/1', 4, 1);
INSERT INTO handle VALUES (2, '123456789/2', 3, 1);
INSERT INTO handle VALUES (3, '123456789/3', 3, 2);
INSERT INTO handle VALUES (4, '123456789/4', 4, 2);
INSERT INTO handle VALUES (5, '123456789/5', 3, 3);
INSERT INTO handle VALUES (6, '123456789/6', 3, 4);
INSERT INTO handle VALUES (7, '123456789/7', 3, 5);
INSERT INTO handle VALUES (8, '123456789/8', 4, 3);
INSERT INTO handle VALUES (9, '123456789/9', 3, 6);
INSERT INTO handle VALUES (10, '123456789/10', 3, 7);
INSERT INTO handle VALUES (11, '123456789/11', 3, 8);
INSERT INTO handle VALUES (12, '123456789/12', 2, 1);
INSERT INTO handle VALUES (13, '123456789/13', 2, 2);
INSERT INTO handle VALUES (14, '123456789/14', 2, 3);
INSERT INTO handle VALUES (15, '123456789/15', 2, 4);
INSERT INTO handle VALUES (16, '123456789/16', 2, 5);
INSERT INTO handle VALUES (17, '123456789/17', 2, 6);
INSERT INTO handle VALUES (18, '123456789/18', 2, 7);
INSERT INTO handle VALUES (19, '123456789/19', 2, 8);
INSERT INTO handle VALUES (20, '123456789/20', 2, 9);
INSERT INTO handle VALUES (21, '123456789/21', 2, 10);
INSERT INTO handle VALUES (22, '123456789/22', 2, 11);
INSERT INTO handle VALUES (23, '123456789/23', 2, 12);
INSERT INTO handle VALUES (24, '123456789/24', 2, 13);
INSERT INTO handle VALUES (25, '123456789/25', 2, 14);
INSERT INTO handle VALUES (26, '123456789/26', 2, 16);
INSERT INTO handle VALUES (27, '123456789/27', 2, 17);
INSERT INTO handle VALUES (28, '123456789/28', 2, 18);
INSERT INTO handle VALUES (29, '123456789/29', 2, 19);
INSERT INTO handle VALUES (30, '123456789/30', 2, 21);
INSERT INTO handle VALUES (31, '123456789/31', 2, 22);
INSERT INTO handle VALUES (32, '123456789/32', 2, 23);
INSERT INTO handle VALUES (33, '123456789/33', 2, 24);


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO item VALUES (11, 1, true, false, '2011-11-08 17:48:42.489+00', 4);
INSERT INTO item VALUES (4, 1, true, false, '2011-11-08 17:26:48.317+00', 1);
INSERT INTO item VALUES (8, 1, true, false, '2011-11-09 17:37:50.775+00', 3);
INSERT INTO item VALUES (1, 1, true, false, '2011-11-09 17:17:31.197+00', 6);
INSERT INTO item VALUES (19, 1, true, false, '2011-11-10 18:07:42.477+00', 6);
INSERT INTO item VALUES (24, 1, true, false, '2011-11-10 18:22:53.685+00', 8);
INSERT INTO item VALUES (12, 1, true, false, '2011-11-10 17:52:03.205+00', 5);
INSERT INTO item VALUES (5, 1, true, false, '2011-11-10 17:28:50.23+00', 2);
INSERT INTO item VALUES (16, 1, true, false, '2011-11-10 18:01:00.147+00', 6);
INSERT INTO item VALUES (2, 1, true, false, '2011-11-10 17:21:38.803+00', 1);
INSERT INTO item VALUES (9, 1, true, false, '2011-11-10 17:42:56.746+00', 4);
INSERT INTO item VALUES (6, 1, true, false, '2011-11-10 17:31:36.877+00', 3);
INSERT INTO item VALUES (3, 1, true, false, '2011-11-10 17:25:45.366+00', 1);
INSERT INTO item VALUES (13, 1, true, false, '2011-11-10 17:53:24.749+00', 5);
INSERT INTO item VALUES (10, 1, true, false, '2011-11-10 17:45:18.601+00', 4);
INSERT INTO item VALUES (17, 1, true, false, '2011-11-10 18:03:55.678+00', 6);
INSERT INTO item VALUES (21, 1, true, false, '2011-11-10 18:17:22.108+00', 7);
INSERT INTO item VALUES (7, 1, true, false, '2011-11-10 17:33:57.047+00', 3);
INSERT INTO item VALUES (14, 1, true, false, '2011-11-10 17:55:54.484+00', 5);
INSERT INTO item VALUES (18, 1, true, false, '2011-11-10 18:05:40.131+00', 6);
INSERT INTO item VALUES (22, 1, true, false, '2011-11-10 18:19:47.41+00', 7);
INSERT INTO item VALUES (23, 1, true, false, '2011-11-10 18:20:43.402+00', 7);


--
-- Data for Name: item2bundle; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO item2bundle VALUES (1, 1, 1);
INSERT INTO item2bundle VALUES (2, 1, 2);
INSERT INTO item2bundle VALUES (3, 2, 3);
INSERT INTO item2bundle VALUES (4, 2, 4);
INSERT INTO item2bundle VALUES (5, 3, 5);
INSERT INTO item2bundle VALUES (6, 3, 6);
INSERT INTO item2bundle VALUES (7, 4, 7);
INSERT INTO item2bundle VALUES (8, 4, 8);
INSERT INTO item2bundle VALUES (9, 5, 9);
INSERT INTO item2bundle VALUES (10, 5, 10);
INSERT INTO item2bundle VALUES (11, 6, 11);
INSERT INTO item2bundle VALUES (12, 6, 12);
INSERT INTO item2bundle VALUES (13, 7, 13);
INSERT INTO item2bundle VALUES (14, 7, 14);
INSERT INTO item2bundle VALUES (15, 8, 15);
INSERT INTO item2bundle VALUES (16, 8, 16);
INSERT INTO item2bundle VALUES (17, 9, 17);
INSERT INTO item2bundle VALUES (18, 9, 18);
INSERT INTO item2bundle VALUES (19, 10, 19);
INSERT INTO item2bundle VALUES (20, 10, 20);
INSERT INTO item2bundle VALUES (21, 11, 21);
INSERT INTO item2bundle VALUES (22, 11, 22);
INSERT INTO item2bundle VALUES (23, 12, 23);
INSERT INTO item2bundle VALUES (24, 12, 24);
INSERT INTO item2bundle VALUES (25, 13, 25);
INSERT INTO item2bundle VALUES (26, 13, 26);
INSERT INTO item2bundle VALUES (27, 14, 27);
INSERT INTO item2bundle VALUES (28, 14, 28);
INSERT INTO item2bundle VALUES (29, 16, 29);
INSERT INTO item2bundle VALUES (30, 16, 30);
INSERT INTO item2bundle VALUES (31, 17, 31);
INSERT INTO item2bundle VALUES (32, 17, 32);
INSERT INTO item2bundle VALUES (33, 18, 33);
INSERT INTO item2bundle VALUES (34, 18, 34);
INSERT INTO item2bundle VALUES (35, 19, 35);
INSERT INTO item2bundle VALUES (36, 19, 36);
INSERT INTO item2bundle VALUES (37, 21, 37);
INSERT INTO item2bundle VALUES (38, 21, 38);
INSERT INTO item2bundle VALUES (39, 22, 39);
INSERT INTO item2bundle VALUES (40, 22, 40);
INSERT INTO item2bundle VALUES (41, 23, 41);
INSERT INTO item2bundle VALUES (42, 23, 42);
INSERT INTO item2bundle VALUES (43, 24, 43);
INSERT INTO item2bundle VALUES (44, 24, 44);


--
-- Data for Name: metadatafieldregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO metadatafieldregistry VALUES (1, 1, 'contributor', NULL, 'A person, organization, or service responsible for the content of the resource.  Catch-all for unspecified contributors.');
INSERT INTO metadatafieldregistry VALUES (2, 1, 'contributor', 'advisor', 'Use primarily for thesis advisor.');
INSERT INTO metadatafieldregistry VALUES (3, 1, 'contributor', 'author', NULL);
INSERT INTO metadatafieldregistry VALUES (4, 1, 'contributor', 'editor', NULL);
INSERT INTO metadatafieldregistry VALUES (5, 1, 'contributor', 'illustrator', NULL);
INSERT INTO metadatafieldregistry VALUES (6, 1, 'contributor', 'other', NULL);
INSERT INTO metadatafieldregistry VALUES (7, 1, 'coverage', 'spatial', 'Spatial characteristics of content.');
INSERT INTO metadatafieldregistry VALUES (8, 1, 'coverage', 'temporal', 'Temporal characteristics of content.');
INSERT INTO metadatafieldregistry VALUES (9, 1, 'creator', NULL, 'Do not use; only for harvested metadata.');
INSERT INTO metadatafieldregistry VALUES (10, 1, 'date', NULL, 'Use qualified form if possible.');
INSERT INTO metadatafieldregistry VALUES (11, 1, 'date', 'accessioned', 'Date DSpace takes possession of item.');
INSERT INTO metadatafieldregistry VALUES (12, 1, 'date', 'available', 'Date or date range item became available to the public.');
INSERT INTO metadatafieldregistry VALUES (13, 1, 'date', 'copyright', 'Date of copyright.');
INSERT INTO metadatafieldregistry VALUES (14, 1, 'date', 'created', 'Date of creation or manufacture of intellectual content if different from date.issued.');
INSERT INTO metadatafieldregistry VALUES (15, 1, 'date', 'issued', 'Date of publication or distribution.');
INSERT INTO metadatafieldregistry VALUES (16, 1, 'date', 'submitted', 'Recommend for theses/dissertations.');
INSERT INTO metadatafieldregistry VALUES (17, 1, 'identifier', NULL, 'Catch-all for unambiguous identifiers not defined by
    qualified form; use identifier.other for a known identifier common
    to a local collection instead of unqualified form.');
INSERT INTO metadatafieldregistry VALUES (18, 1, 'identifier', 'citation', 'Human-readable, standard bibliographic citation 
    of non-DSpace format of this item');
INSERT INTO metadatafieldregistry VALUES (19, 1, 'identifier', 'govdoc', 'A government document number');
INSERT INTO metadatafieldregistry VALUES (20, 1, 'identifier', 'isbn', 'International Standard Book Number');
INSERT INTO metadatafieldregistry VALUES (21, 1, 'identifier', 'issn', 'International Standard Serial Number');
INSERT INTO metadatafieldregistry VALUES (22, 1, 'identifier', 'sici', 'Serial Item and Contribution Identifier');
INSERT INTO metadatafieldregistry VALUES (23, 1, 'identifier', 'ismn', 'International Standard Music Number');
INSERT INTO metadatafieldregistry VALUES (24, 1, 'identifier', 'other', 'A known identifier type common to a local collection.');
INSERT INTO metadatafieldregistry VALUES (25, 1, 'identifier', 'uri', 'Uniform Resource Identifier');
INSERT INTO metadatafieldregistry VALUES (26, 1, 'description', NULL, 'Catch-all for any description not defined by qualifiers.');
INSERT INTO metadatafieldregistry VALUES (27, 1, 'description', 'abstract', 'Abstract or summary.');
INSERT INTO metadatafieldregistry VALUES (28, 1, 'description', 'provenance', 'The history of custody of the item since its creation, including any changes successive custodians made to it.');
INSERT INTO metadatafieldregistry VALUES (29, 1, 'description', 'sponsorship', 'Information about sponsoring agencies, individuals, or
    contractual arrangements for the item.');
INSERT INTO metadatafieldregistry VALUES (30, 1, 'description', 'statementofresponsibility', 'To preserve statement of responsibility from MARC records.');
INSERT INTO metadatafieldregistry VALUES (31, 1, 'description', 'tableofcontents', 'A table of contents for a given item.');
INSERT INTO metadatafieldregistry VALUES (32, 1, 'description', 'uri', 'Uniform Resource Identifier pointing to description of
    this item.');
INSERT INTO metadatafieldregistry VALUES (33, 1, 'format', NULL, 'Catch-all for any format information not defined by qualifiers.');
INSERT INTO metadatafieldregistry VALUES (34, 1, 'format', 'extent', 'Size or duration.');
INSERT INTO metadatafieldregistry VALUES (35, 1, 'format', 'medium', 'Physical medium.');
INSERT INTO metadatafieldregistry VALUES (36, 1, 'format', 'mimetype', 'Registered MIME type identifiers.');
INSERT INTO metadatafieldregistry VALUES (37, 1, 'language', NULL, 'Catch-all for non-ISO forms of the language of the
    item, accommodating harvested values.');
INSERT INTO metadatafieldregistry VALUES (38, 1, 'language', 'iso', 'Current ISO standard for language of intellectual content, including country codes (e.g. "en_US").');
INSERT INTO metadatafieldregistry VALUES (39, 1, 'publisher', NULL, 'Entity responsible for publication, distribution, or imprint.');
INSERT INTO metadatafieldregistry VALUES (40, 1, 'relation', NULL, 'Catch-all for references to other related items.');
INSERT INTO metadatafieldregistry VALUES (41, 1, 'relation', 'isformatof', 'References additional physical form.');
INSERT INTO metadatafieldregistry VALUES (42, 1, 'relation', 'ispartof', 'References physically or logically containing item.');
INSERT INTO metadatafieldregistry VALUES (43, 1, 'relation', 'ispartofseries', 'Series name and number within that series, if available.');
INSERT INTO metadatafieldregistry VALUES (44, 1, 'relation', 'haspart', 'References physically or logically contained item.');
INSERT INTO metadatafieldregistry VALUES (45, 1, 'relation', 'isversionof', 'References earlier version.');
INSERT INTO metadatafieldregistry VALUES (46, 1, 'relation', 'hasversion', 'References later version.');
INSERT INTO metadatafieldregistry VALUES (47, 1, 'relation', 'isbasedon', 'References source.');
INSERT INTO metadatafieldregistry VALUES (48, 1, 'relation', 'isreferencedby', 'Pointed to by referenced resource.');
INSERT INTO metadatafieldregistry VALUES (49, 1, 'relation', 'requires', 'Referenced resource is required to support function,
    delivery, or coherence of item.');
INSERT INTO metadatafieldregistry VALUES (50, 1, 'relation', 'replaces', 'References preceeding item.');
INSERT INTO metadatafieldregistry VALUES (51, 1, 'relation', 'isreplacedby', 'References succeeding item.');
INSERT INTO metadatafieldregistry VALUES (52, 1, 'relation', 'uri', 'References Uniform Resource Identifier for related item.');
INSERT INTO metadatafieldregistry VALUES (53, 1, 'rights', NULL, 'Terms governing use and reproduction.');
INSERT INTO metadatafieldregistry VALUES (54, 1, 'rights', 'uri', 'References terms governing use and reproduction.');
INSERT INTO metadatafieldregistry VALUES (55, 1, 'source', NULL, 'Do not use; only for harvested metadata.');
INSERT INTO metadatafieldregistry VALUES (56, 1, 'source', 'uri', 'Do not use; only for harvested metadata.');
INSERT INTO metadatafieldregistry VALUES (57, 1, 'subject', NULL, 'Uncontrolled index term.');
INSERT INTO metadatafieldregistry VALUES (58, 1, 'subject', 'classification', 'Catch-all for value from local classification system;
    global classification systems will receive specific qualifier');
INSERT INTO metadatafieldregistry VALUES (59, 1, 'subject', 'ddc', 'Dewey Decimal Classification Number');
INSERT INTO metadatafieldregistry VALUES (60, 1, 'subject', 'lcc', 'Library of Congress Classification Number');
INSERT INTO metadatafieldregistry VALUES (61, 1, 'subject', 'lcsh', 'Library of Congress Subject Headings');
INSERT INTO metadatafieldregistry VALUES (62, 1, 'subject', 'mesh', 'MEdical Subject Headings');
INSERT INTO metadatafieldregistry VALUES (63, 1, 'subject', 'other', 'Local controlled vocabulary; global vocabularies will receive specific qualifier.');
INSERT INTO metadatafieldregistry VALUES (64, 1, 'title', NULL, 'Title statement/title proper.');
INSERT INTO metadatafieldregistry VALUES (65, 1, 'title', 'alternative', 'Varying (or substitute) form of title proper appearing in item,
    e.g. abbreviation or translation');
INSERT INTO metadatafieldregistry VALUES (66, 1, 'type', NULL, 'Nature or genre of content.');
INSERT INTO metadatafieldregistry VALUES (67, 1, 'date', 'updated', 'The last time the item was updated via the SWORD interface');
INSERT INTO metadatafieldregistry VALUES (68, 1, 'description', 'version', 'The Peer Reviewed status of an item');
INSERT INTO metadatafieldregistry VALUES (69, 1, 'identifier', 'slug', 'a uri supplied via the sword slug header, as a suggested uri for the item');
INSERT INTO metadatafieldregistry VALUES (70, 1, 'language', 'rfc3066', 'the rfc3066 form of the language for the item');
INSERT INTO metadatafieldregistry VALUES (71, 1, 'rights', 'holder', 'The owner of the copyright');


--
-- Data for Name: metadataschemaregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO metadataschemaregistry VALUES (1, 'http://dublincore.org/documents/dcmi-terms/', 'dc');


--
-- Data for Name: metadatavalue; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO metadatavalue VALUES (1, 1, 3, 'smith, fred', NULL, 1);
INSERT INTO metadatavalue VALUES (2, 1, 64, 'rails guide', 'en_US', 1);
INSERT INTO metadatavalue VALUES (3, 1, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (4, 1, 38, 'fr', 'en_US', 1);
INSERT INTO metadatavalue VALUES (5, 1, 57, 'ruby', 'en_US', 1);
INSERT INTO metadatavalue VALUES (6, 1, 57, 'rails', 'en_US', 2);
INSERT INTO metadatavalue VALUES (7, 1, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-09T17:17:31Z
No. of bitstreams: 1
rails_guides.txt: 17 bytes, checksum: 03395dc638b0566daed65002bb061cf9 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (8, 1, 11, '2011-11-09T17:17:31Z', NULL, 1);
INSERT INTO metadatavalue VALUES (9, 1, 12, '2011-11-09T17:17:31Z', NULL, 1);
INSERT INTO metadatavalue VALUES (10, 1, 15, '2011-11-09T17:17:31Z', NULL, 1);
INSERT INTO metadatavalue VALUES (11, 1, 25, 'http://hdl.handle.net/123456789/12', NULL, 1);
INSERT INTO metadatavalue VALUES (12, 1, 28, 'Made available in DSpace on 2011-11-09T17:17:31Z (GMT). No. of bitstreams: 1
rails_guides.txt: 17 bytes, checksum: 03395dc638b0566daed65002bb061cf9 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (13, 2, 3, 'rooney, wayne', NULL, 1);
INSERT INTO metadatavalue VALUES (14, 2, 64, 'rails 3 in action', 'en_US', 1);
INSERT INTO metadatavalue VALUES (15, 2, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (16, 2, 57, 'action', 'en_US', 1);
INSERT INTO metadatavalue VALUES (17, 2, 57, 'book', 'en_US', 2);
INSERT INTO metadatavalue VALUES (18, 2, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:21:38Z
No. of bitstreams: 1
r3ia.txt: 9 bytes, checksum: 189e58744a3ffb0524bd81619800df90 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (19, 2, 11, '2011-11-10T17:21:38Z', NULL, 1);
INSERT INTO metadatavalue VALUES (20, 2, 12, '2011-11-10T17:21:38Z', NULL, 1);
INSERT INTO metadatavalue VALUES (21, 2, 15, '2011-11-10T17:21:38Z', NULL, 1);
INSERT INTO metadatavalue VALUES (22, 2, 25, 'http://hdl.handle.net/123456789/13', NULL, 1);
INSERT INTO metadatavalue VALUES (23, 2, 28, 'Made available in DSpace on 2011-11-10T17:21:38Z (GMT). No. of bitstreams: 1
r3ia.txt: 9 bytes, checksum: 189e58744a3ffb0524bd81619800df90 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (25, 3, 64, 'rails tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (26, 3, 66, 'Technical Report', 'en_US', 1);
INSERT INTO metadatavalue VALUES (27, 3, 38, 'zh', 'en_US', 1);
INSERT INTO metadatavalue VALUES (28, 3, 3, 'brown', NULL, 1);
INSERT INTO metadatavalue VALUES (29, 3, 15, '1976-04-24', NULL, 1);
INSERT INTO metadatavalue VALUES (30, 3, 39, 'pearson', 'en_US', 1);
INSERT INTO metadatavalue VALUES (31, 3, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:25:45Z
No. of bitstreams: 1
rails_tutorial.txt: 19 bytes, checksum: ff21fdbe9c1b7972f43411ce7cd9e80a (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (32, 3, 11, '2011-11-10T17:25:45Z', NULL, 1);
INSERT INTO metadatavalue VALUES (33, 3, 12, '2011-11-10T17:25:45Z', NULL, 1);
INSERT INTO metadatavalue VALUES (34, 3, 25, 'http://hdl.handle.net/123456789/14', NULL, 1);
INSERT INTO metadatavalue VALUES (35, 3, 28, 'Made available in DSpace on 2011-11-10T17:25:45Z (GMT). No. of bitstreams: 1
rails_tutorial.txt: 19 bytes, checksum: ff21fdbe9c1b7972f43411ce7cd9e80a (MD5)
  Previous issue date: 1976-04-24', 'en', 2);
INSERT INTO metadatavalue VALUES (36, 4, 3, 'burns, paul', NULL, 1);
INSERT INTO metadatavalue VALUES (37, 4, 64, 'agile web development with rails', 'en_US', 1);
INSERT INTO metadatavalue VALUES (38, 4, 66, 'Dataset', 'en_US', 1);
INSERT INTO metadatavalue VALUES (39, 4, 38, 'es', 'en_US', 1);
INSERT INTO metadatavalue VALUES (40, 4, 57, 'another rails book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (41, 4, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-08T17:26:48Z
No. of bitstreams: 1
awdwr.txt: 10 bytes, checksum: 01ef867ced6e7b0c8966211650adeee5 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (42, 4, 11, '2011-11-08T17:26:48Z', NULL, 1);
INSERT INTO metadatavalue VALUES (43, 4, 12, '2011-11-08T17:26:48Z', NULL, 1);
INSERT INTO metadatavalue VALUES (44, 4, 15, '2011-11-08T17:26:48Z', NULL, 1);
INSERT INTO metadatavalue VALUES (45, 4, 25, 'http://hdl.handle.net/123456789/15', NULL, 1);
INSERT INTO metadatavalue VALUES (46, 4, 28, 'Made available in DSpace on 2011-11-08T17:26:48Z (GMT). No. of bitstreams: 1
awdwr.txt: 10 bytes, checksum: 01ef867ced6e7b0c8966211650adeee5 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (47, 5, 3, 'sinatra, frank', NULL, 1);
INSERT INTO metadatavalue VALUES (48, 5, 64, 'sinatra tutorials by fankie', 'en_US', 1);
INSERT INTO metadatavalue VALUES (49, 5, 66, 'Musical Score', 'en_US', 1);
INSERT INTO metadatavalue VALUES (50, 5, 38, 'it', 'en_US', 1);
INSERT INTO metadatavalue VALUES (51, 5, 57, 'sinatra', 'en_US', 1);
INSERT INTO metadatavalue VALUES (52, 5, 57, 'lighweight rack web framework', 'en_US', 2);
INSERT INTO metadatavalue VALUES (53, 5, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:28:50Z
No. of bitstreams: 1
sinatra_tutorial.txt: 21 bytes, checksum: 862fa51008cec90d681fbb291c56abd1 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (54, 5, 11, '2011-11-10T17:28:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (55, 5, 12, '2011-11-10T17:28:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (56, 5, 15, '2011-11-10T17:28:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (57, 5, 25, 'http://hdl.handle.net/123456789/16', NULL, 1);
INSERT INTO metadatavalue VALUES (58, 5, 28, 'Made available in DSpace on 2011-11-10T17:28:50Z (GMT). No. of bitstreams: 1
sinatra_tutorial.txt: 21 bytes, checksum: 862fa51008cec90d681fbb291c56abd1 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (59, 6, 3, 'springer, jerry', NULL, 1);
INSERT INTO metadatavalue VALUES (60, 6, 64, 'ioc and di', 'en_US', 1);
INSERT INTO metadatavalue VALUES (61, 6, 66, 'Animation', 'en_US', 1);
INSERT INTO metadatavalue VALUES (62, 6, 38, 'es', 'en_US', 1);
INSERT INTO metadatavalue VALUES (63, 6, 57, 'dependency injection', 'en_US', 1);
INSERT INTO metadatavalue VALUES (64, 6, 57, 'inversion of control', 'en_US', 2);
INSERT INTO metadatavalue VALUES (65, 6, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:31:36Z
No. of bitstreams: 2
ioc.txt: 8 bytes, checksum: 671c8bf2d92e1fdd7f809da10f007399 (MD5)
di.txt: 7 bytes, checksum: 24c6cbbfd4215b77f96aaa8b11072b7c (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (66, 6, 11, '2011-11-10T17:31:36Z', NULL, 1);
INSERT INTO metadatavalue VALUES (67, 6, 12, '2011-11-10T17:31:36Z', NULL, 1);
INSERT INTO metadatavalue VALUES (68, 6, 15, '2011-11-10T17:31:36Z', NULL, 1);
INSERT INTO metadatavalue VALUES (69, 6, 25, 'http://hdl.handle.net/123456789/17', NULL, 1);
INSERT INTO metadatavalue VALUES (70, 6, 28, 'Made available in DSpace on 2011-11-10T17:31:36Z (GMT). No. of bitstreams: 2
ioc.txt: 8 bytes, checksum: 671c8bf2d92e1fdd7f809da10f007399 (MD5)
di.txt: 7 bytes, checksum: 24c6cbbfd4215b77f96aaa8b11072b7c (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (71, 7, 3, 'Mercury, Freddie', NULL, 1);
INSERT INTO metadatavalue VALUES (72, 7, 64, 'guides and tutorials', 'en_US', 1);
INSERT INTO metadatavalue VALUES (73, 7, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (74, 7, 38, 'en', 'en_US', 1);
INSERT INTO metadatavalue VALUES (75, 7, 57, 'spring', 'en_US', 1);
INSERT INTO metadatavalue VALUES (76, 7, 57, 'tutorials and guides', 'en_US', 2);
INSERT INTO metadatavalue VALUES (77, 7, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:33:57Z
No. of bitstreams: 2
spring_guides.txt: 18 bytes, checksum: 18f2f8ae03cf0129490c592c4424bc04 (MD5)
spring_tutorial.txt: 20 bytes, checksum: 9d1be970259d826ed105a1c2740798d6 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (78, 7, 11, '2011-11-10T17:33:57Z', NULL, 1);
INSERT INTO metadatavalue VALUES (79, 7, 12, '2011-11-10T17:33:57Z', NULL, 1);
INSERT INTO metadatavalue VALUES (80, 7, 15, '2011-11-10T17:33:57Z', NULL, 1);
INSERT INTO metadatavalue VALUES (81, 7, 25, 'http://hdl.handle.net/123456789/18', NULL, 1);
INSERT INTO metadatavalue VALUES (82, 7, 28, 'Made available in DSpace on 2011-11-10T17:33:57Z (GMT). No. of bitstreams: 2
spring_guides.txt: 18 bytes, checksum: 18f2f8ae03cf0129490c592c4424bc04 (MD5)
spring_tutorial.txt: 20 bytes, checksum: 9d1be970259d826ed105a1c2740798d6 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (83, 8, 3, 'Senna, Ayrton', NULL, 1);
INSERT INTO metadatavalue VALUES (84, 8, 64, 'OMG its ORM!!!', 'en_US', 1);
INSERT INTO metadatavalue VALUES (85, 8, 66, 'Working Paper', 'en_US', 1);
INSERT INTO metadatavalue VALUES (86, 8, 57, 'ORM', 'en_US', 1);
INSERT INTO metadatavalue VALUES (87, 8, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-09T17:37:50Z
No. of bitstreams: 1
orm.txt: 8 bytes, checksum: 4e6ad61b7c95e6f1926f837422619850 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (88, 8, 11, '2011-11-09T17:37:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (89, 8, 12, '2011-11-09T17:37:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (90, 8, 15, '2011-11-09T17:37:50Z', NULL, 1);
INSERT INTO metadatavalue VALUES (91, 8, 25, 'http://hdl.handle.net/123456789/19', NULL, 1);
INSERT INTO metadatavalue VALUES (92, 8, 28, 'Made available in DSpace on 2011-11-09T17:37:50Z (GMT). No. of bitstreams: 1
orm.txt: 8 bytes, checksum: 4e6ad61b7c95e6f1926f837422619850 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (93, 9, 3, 'Zuck, Mark', NULL, 1);
INSERT INTO metadatavalue VALUES (94, 9, 64, 'JDBC Zuck''s', 'en_US', 1);
INSERT INTO metadatavalue VALUES (95, 9, 66, 'Book chapter', 'en_US', 1);
INSERT INTO metadatavalue VALUES (96, 9, 38, 'en_US', 'en_US', 1);
INSERT INTO metadatavalue VALUES (97, 9, 57, 'JDBC', 'en_US', 1);
INSERT INTO metadatavalue VALUES (98, 9, 57, 'hibernate', 'en_US', 2);
INSERT INTO metadatavalue VALUES (99, 9, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:42:56Z
No. of bitstreams: 1
jdbc.txt: 9 bytes, checksum: 73d08e075748d40cf205eaa490941600 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (100, 9, 11, '2011-11-10T17:42:56Z', NULL, 1);
INSERT INTO metadatavalue VALUES (101, 9, 12, '2011-11-10T17:42:56Z', NULL, 1);
INSERT INTO metadatavalue VALUES (102, 9, 15, '2011-11-10T17:42:56Z', NULL, 1);
INSERT INTO metadatavalue VALUES (103, 9, 25, 'http://hdl.handle.net/123456789/20', NULL, 1);
INSERT INTO metadatavalue VALUES (104, 9, 28, 'Made available in DSpace on 2011-11-10T17:42:56Z (GMT). No. of bitstreams: 1
jdbc.txt: 9 bytes, checksum: 73d08e075748d40cf205eaa490941600 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (105, 10, 3, 'Tutor, Hibernate', NULL, 1);
INSERT INTO metadatavalue VALUES (106, 10, 64, 'Hibernate tutorials', 'en_US', 1);
INSERT INTO metadatavalue VALUES (107, 10, 66, 'Learning Object', 'en_US', 1);
INSERT INTO metadatavalue VALUES (108, 10, 38, 'en', 'en_US', 1);
INSERT INTO metadatavalue VALUES (109, 10, 57, 'Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (110, 10, 57, 'hibernate', 'en_US', 2);
INSERT INTO metadatavalue VALUES (111, 10, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:45:18Z
No. of bitstreams: 1
hibernate-tutorial.txt: 23 bytes, checksum: 3be099dec9392a81a4a5bac0fa888c7b (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (112, 10, 11, '2011-11-10T17:45:18Z', NULL, 1);
INSERT INTO metadatavalue VALUES (113, 10, 12, '2011-11-10T17:45:18Z', NULL, 1);
INSERT INTO metadatavalue VALUES (114, 10, 15, '2011-11-10T17:45:18Z', NULL, 1);
INSERT INTO metadatavalue VALUES (115, 10, 25, 'http://hdl.handle.net/123456789/21', NULL, 1);
INSERT INTO metadatavalue VALUES (116, 10, 28, 'Made available in DSpace on 2011-11-10T17:45:18Z (GMT). No. of bitstreams: 1
hibernate-tutorial.txt: 23 bytes, checksum: 3be099dec9392a81a4a5bac0fa888c7b (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (117, 11, 3, 'Sauce, Tartare', NULL, 1);
INSERT INTO metadatavalue VALUES (118, 11, 64, 'SQL and Driver', 'en_US', 1);
INSERT INTO metadatavalue VALUES (119, 11, 66, 'Preprint', 'en_US', 1);
INSERT INTO metadatavalue VALUES (120, 11, 38, 'en_US', 'en_US', 1);
INSERT INTO metadatavalue VALUES (121, 11, 57, 'SQL', 'en_US', 1);
INSERT INTO metadatavalue VALUES (122, 11, 57, 'Driver', 'en_US', 2);
INSERT INTO metadatavalue VALUES (123, 11, 57, 'Hibernate', 'en_US', 3);
INSERT INTO metadatavalue VALUES (124, 11, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-08T17:48:42Z
No. of bitstreams: 2
sql.txt: 8 bytes, checksum: 71f47ea290e55742c5992996e49fa1f3 (MD5)
driver.txt: 11 bytes, checksum: 68b4f1665cba6fb5ec05ddd81326006e (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (125, 11, 11, '2011-11-08T17:48:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (126, 11, 12, '2011-11-08T17:48:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (127, 11, 15, '2011-11-08T17:48:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (128, 11, 25, 'http://hdl.handle.net/123456789/22', NULL, 1);
INSERT INTO metadatavalue VALUES (129, 11, 28, 'Made available in DSpace on 2011-11-08T17:48:42Z (GMT). No. of bitstreams: 2
sql.txt: 8 bytes, checksum: 71f47ea290e55742c5992996e49fa1f3 (MD5)
driver.txt: 11 bytes, checksum: 68b4f1665cba6fb5ec05ddd81326006e (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (130, 12, 3, 'Lopez, Maria', NULL, 1);
INSERT INTO metadatavalue VALUES (131, 12, 64, 'Check out my POM', 'en_US', 1);
INSERT INTO metadatavalue VALUES (132, 12, 66, 'Image', 'en_US', 1);
INSERT INTO metadatavalue VALUES (133, 12, 38, 'es', 'en_US', 1);
INSERT INTO metadatavalue VALUES (134, 12, 57, 'POM.xml', 'en_US', 1);
INSERT INTO metadatavalue VALUES (135, 12, 57, 'Maven', 'en_US', 2);
INSERT INTO metadatavalue VALUES (136, 12, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:52:03Z
No. of bitstreams: 1
pom.txt: 8 bytes, checksum: f96725ffe5336c8bbda7d3fef04a076b (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (137, 12, 11, '2011-11-10T17:52:03Z', NULL, 1);
INSERT INTO metadatavalue VALUES (138, 12, 12, '2011-11-10T17:52:03Z', NULL, 1);
INSERT INTO metadatavalue VALUES (139, 12, 15, '2011-11-10T17:52:03Z', NULL, 1);
INSERT INTO metadatavalue VALUES (140, 12, 25, 'http://hdl.handle.net/123456789/23', NULL, 1);
INSERT INTO metadatavalue VALUES (141, 12, 28, 'Made available in DSpace on 2011-11-10T17:52:03Z (GMT). No. of bitstreams: 1
pom.txt: 8 bytes, checksum: f96725ffe5336c8bbda7d3fef04a076b (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (142, 13, 3, 'Jones, Indiana', NULL, 1);
INSERT INTO metadatavalue VALUES (143, 13, 64, 'Dependencies guide', 'en_US', 1);
INSERT INTO metadatavalue VALUES (144, 13, 66, 'Technical Report', 'en_US', 1);
INSERT INTO metadatavalue VALUES (145, 13, 38, 'de', 'en_US', 1);
INSERT INTO metadatavalue VALUES (146, 13, 57, 'dependency', 'en_US', 1);
INSERT INTO metadatavalue VALUES (147, 13, 57, 'Maven', 'en_US', 2);
INSERT INTO metadatavalue VALUES (148, 13, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:53:24Z
No. of bitstreams: 1
dependancies.txt: 17 bytes, checksum: c6a71481dfcb588c10a82b07fbb4b6e0 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (149, 13, 11, '2011-11-10T17:53:24Z', NULL, 1);
INSERT INTO metadatavalue VALUES (150, 13, 12, '2011-11-10T17:53:24Z', NULL, 1);
INSERT INTO metadatavalue VALUES (151, 13, 15, '2011-11-10T17:53:24Z', NULL, 1);
INSERT INTO metadatavalue VALUES (152, 13, 25, 'http://hdl.handle.net/123456789/24', NULL, 1);
INSERT INTO metadatavalue VALUES (153, 13, 28, 'Made available in DSpace on 2011-11-10T17:53:24Z (GMT). No. of bitstreams: 1
dependancies.txt: 17 bytes, checksum: c6a71481dfcb588c10a82b07fbb4b6e0 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (154, 14, 3, 'Falcon, Millenium', NULL, 1);
INSERT INTO metadatavalue VALUES (155, 14, 64, 'Maven tutorials', 'en_US', 1);
INSERT INTO metadatavalue VALUES (156, 14, 66, 'Book chapter', 'en_US', 1);
INSERT INTO metadatavalue VALUES (157, 14, 38, 'en', 'en_US', 1);
INSERT INTO metadatavalue VALUES (158, 14, 57, 'Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (159, 14, 57, 'Maven', 'en_US', 2);
INSERT INTO metadatavalue VALUES (160, 14, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T17:55:54Z
No. of bitstreams: 1
maven_tutorial.txt: 19 bytes, checksum: f7291aa12944442814e2843782a12191 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (161, 14, 11, '2011-11-10T17:55:54Z', NULL, 1);
INSERT INTO metadatavalue VALUES (162, 14, 12, '2011-11-10T17:55:54Z', NULL, 1);
INSERT INTO metadatavalue VALUES (163, 14, 15, '2011-11-10T17:55:54Z', NULL, 1);
INSERT INTO metadatavalue VALUES (164, 14, 25, 'http://hdl.handle.net/123456789/25', NULL, 1);
INSERT INTO metadatavalue VALUES (165, 14, 28, 'Made available in DSpace on 2011-11-10T17:55:54Z (GMT). No. of bitstreams: 1
maven_tutorial.txt: 19 bytes, checksum: f7291aa12944442814e2843782a12191 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (166, 16, 3, 'Harper, Charlie', NULL, 1);
INSERT INTO metadatavalue VALUES (167, 16, 64, 'Scrum Master Certification Guide', 'en_US', 1);
INSERT INTO metadatavalue VALUES (168, 16, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (169, 16, 38, 'es', 'en_US', 1);
INSERT INTO metadatavalue VALUES (170, 16, 57, 'SMC', 'en_US', 1);
INSERT INTO metadatavalue VALUES (171, 16, 57, 'Scrum', 'en_US', 2);
INSERT INTO metadatavalue VALUES (172, 16, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:01:00Z
No. of bitstreams: 1
smc.txt: 8 bytes, checksum: 99773f426a678c374b2e455affb11638 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (173, 16, 11, '2011-11-10T18:01:00Z', NULL, 1);
INSERT INTO metadatavalue VALUES (174, 16, 12, '2011-11-10T18:01:00Z', NULL, 1);
INSERT INTO metadatavalue VALUES (175, 16, 15, '2011-11-10T18:01:00Z', NULL, 1);
INSERT INTO metadatavalue VALUES (176, 16, 25, 'http://hdl.handle.net/123456789/26', NULL, 1);
INSERT INTO metadatavalue VALUES (177, 16, 28, 'Made available in DSpace on 2011-11-10T18:01:00Z (GMT). No. of bitstreams: 1
smc.txt: 8 bytes, checksum: 99773f426a678c374b2e455affb11638 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (178, 17, 3, 'Bolt, Usain', NULL, 1);
INSERT INTO metadatavalue VALUES (179, 17, 64, 'Jamaican Sprints', 'en_US', 1);
INSERT INTO metadatavalue VALUES (180, 17, 38, 'en_US', 'en_US', 1);
INSERT INTO metadatavalue VALUES (181, 17, 57, 'Sprint', 'en_US', 1);
INSERT INTO metadatavalue VALUES (182, 17, 57, 'Scrum', 'en_US', 2);
INSERT INTO metadatavalue VALUES (183, 17, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:03:55Z
No. of bitstreams: 1
sprints.txt: 12 bytes, checksum: 4ba73ddd9107ad708acbbba27f3bcd4a (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (184, 17, 11, '2011-11-10T18:03:55Z', NULL, 1);
INSERT INTO metadatavalue VALUES (185, 17, 12, '2011-11-10T18:03:55Z', NULL, 1);
INSERT INTO metadatavalue VALUES (186, 17, 15, '2011-11-10T18:03:55Z', NULL, 1);
INSERT INTO metadatavalue VALUES (187, 17, 25, 'http://hdl.handle.net/123456789/27', NULL, 1);
INSERT INTO metadatavalue VALUES (188, 17, 28, 'Made available in DSpace on 2011-11-10T18:03:55Z (GMT). No. of bitstreams: 1
sprints.txt: 12 bytes, checksum: 4ba73ddd9107ad708acbbba27f3bcd4a (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (189, 18, 3, 'Sugar, Alan', NULL, 1);
INSERT INTO metadatavalue VALUES (190, 18, 64, 'Product Backlog book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (191, 18, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (192, 18, 38, 'en', 'en_US', 1);
INSERT INTO metadatavalue VALUES (193, 18, 57, 'Product Backlog', 'en_US', 1);
INSERT INTO metadatavalue VALUES (194, 18, 57, 'Scrum', 'en_US', 2);
INSERT INTO metadatavalue VALUES (195, 18, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:05:40Z
No. of bitstreams: 1
product_backlog.txt: 20 bytes, checksum: cd7df045cf63366dd3aeb60487e17548 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (196, 18, 11, '2011-11-10T18:05:40Z', NULL, 1);
INSERT INTO metadatavalue VALUES (197, 18, 12, '2011-11-10T18:05:40Z', NULL, 1);
INSERT INTO metadatavalue VALUES (198, 18, 15, '2011-11-10T18:05:40Z', NULL, 1);
INSERT INTO metadatavalue VALUES (199, 18, 25, 'http://hdl.handle.net/123456789/28', NULL, 1);
INSERT INTO metadatavalue VALUES (200, 18, 28, 'Made available in DSpace on 2011-11-10T18:05:40Z (GMT). No. of bitstreams: 1
product_backlog.txt: 20 bytes, checksum: cd7df045cf63366dd3aeb60487e17548 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (201, 19, 3, 'Beck, Martin', NULL, 1);
INSERT INTO metadatavalue VALUES (202, 19, 64, 'Scrum Tutorials', 'en_US', 1);
INSERT INTO metadatavalue VALUES (203, 19, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (204, 19, 38, 'de', 'en_US', 1);
INSERT INTO metadatavalue VALUES (205, 19, 57, 'Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (206, 19, 57, 'Scrum', 'en_US', 2);
INSERT INTO metadatavalue VALUES (207, 19, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:07:42Z
No. of bitstreams: 1
scrum_tutorial.txt: 19 bytes, checksum: eb756583f982101e713c5d416e912fe0 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (208, 19, 11, '2011-11-10T18:07:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (209, 19, 12, '2011-11-10T18:07:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (210, 19, 15, '2011-11-10T18:07:42Z', NULL, 1);
INSERT INTO metadatavalue VALUES (211, 19, 25, 'http://hdl.handle.net/123456789/29', NULL, 1);
INSERT INTO metadatavalue VALUES (212, 19, 28, 'Made available in DSpace on 2011-11-10T18:07:42Z (GMT). No. of bitstreams: 1
scrum_tutorial.txt: 19 bytes, checksum: eb756583f982101e713c5d416e912fe0 (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (213, 21, 3, 'Fowler, Martin', NULL, 1);
INSERT INTO metadatavalue VALUES (214, 21, 64, 'Programming in Pairs', 'en_US', 1);
INSERT INTO metadatavalue VALUES (215, 21, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (216, 21, 38, 'en_US', 'en_US', 1);
INSERT INTO metadatavalue VALUES (217, 21, 57, 'Pair Programming', 'en_US', 1);
INSERT INTO metadatavalue VALUES (218, 21, 57, 'Scrum', 'en_US', 2);
INSERT INTO metadatavalue VALUES (219, 21, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:17:22Z
No. of bitstreams: 1
pair_programming.txt: 21 bytes, checksum: cafa6e6c9caf9bd7ce4158833cabe3cd (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (220, 21, 11, '2011-11-10T18:17:22Z', NULL, 1);
INSERT INTO metadatavalue VALUES (221, 21, 12, '2011-11-10T18:17:22Z', NULL, 1);
INSERT INTO metadatavalue VALUES (222, 21, 15, '2011-11-10T18:17:22Z', NULL, 1);
INSERT INTO metadatavalue VALUES (223, 21, 25, 'http://hdl.handle.net/123456789/30', NULL, 1);
INSERT INTO metadatavalue VALUES (224, 21, 28, 'Made available in DSpace on 2011-11-10T18:17:22Z (GMT). No. of bitstreams: 1
pair_programming.txt: 21 bytes, checksum: cafa6e6c9caf9bd7ce4158833cabe3cd (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (225, 22, 3, 'Beck, Kent', NULL, 1);
INSERT INTO metadatavalue VALUES (226, 22, 64, 'TDD by example', 'en_US', 1);
INSERT INTO metadatavalue VALUES (227, 22, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (228, 22, 38, 'en', 'en_US', 1);
INSERT INTO metadatavalue VALUES (229, 22, 57, 'Test Driven Development', 'en_US', 1);
INSERT INTO metadatavalue VALUES (230, 22, 57, 'XP', 'en_US', 2);
INSERT INTO metadatavalue VALUES (231, 22, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:19:47Z
No. of bitstreams: 1
tdd.txt: 8 bytes, checksum: 36f6aa3d1c0da471007c99684d73468b (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (232, 22, 11, '2011-11-10T18:19:47Z', NULL, 1);
INSERT INTO metadatavalue VALUES (233, 22, 12, '2011-11-10T18:19:47Z', NULL, 1);
INSERT INTO metadatavalue VALUES (234, 22, 15, '2011-11-10T18:19:47Z', NULL, 1);
INSERT INTO metadatavalue VALUES (235, 22, 25, 'http://hdl.handle.net/123456789/31', NULL, 1);
INSERT INTO metadatavalue VALUES (236, 22, 28, 'Made available in DSpace on 2011-11-10T18:19:47Z (GMT). No. of bitstreams: 1
tdd.txt: 8 bytes, checksum: 36f6aa3d1c0da471007c99684d73468b (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (237, 23, 3, 'Davies, Rachel', NULL, 1);
INSERT INTO metadatavalue VALUES (238, 23, 64, 'XP Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (239, 23, 66, 'Book', 'en_US', 1);
INSERT INTO metadatavalue VALUES (240, 23, 38, 'en_US', 'en_US', 1);
INSERT INTO metadatavalue VALUES (241, 23, 57, 'Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (242, 23, 57, 'XP', 'en_US', 2);
INSERT INTO metadatavalue VALUES (243, 23, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:20:43Z
No. of bitstreams: 1
xp_tutorial.txt: 16 bytes, checksum: e5325f646ecf2ac18e0177e1797abebf (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (244, 23, 11, '2011-11-10T18:20:43Z', NULL, 1);
INSERT INTO metadatavalue VALUES (245, 23, 12, '2011-11-10T18:20:43Z', NULL, 1);
INSERT INTO metadatavalue VALUES (246, 23, 15, '2011-11-10T18:20:43Z', NULL, 1);
INSERT INTO metadatavalue VALUES (247, 23, 25, 'http://hdl.handle.net/123456789/32', NULL, 1);
INSERT INTO metadatavalue VALUES (248, 23, 28, 'Made available in DSpace on 2011-11-10T18:20:43Z (GMT). No. of bitstreams: 1
xp_tutorial.txt: 16 bytes, checksum: e5325f646ecf2ac18e0177e1797abebf (MD5)', 'en', 2);
INSERT INTO metadatavalue VALUES (249, 24, 3, 'Zhao, Lau', NULL, 1);
INSERT INTO metadatavalue VALUES (250, 24, 64, 'Waterfall Model Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (251, 24, 66, 'Presentation', 'en_US', 1);
INSERT INTO metadatavalue VALUES (252, 24, 38, 'zh', 'en_US', 1);
INSERT INTO metadatavalue VALUES (253, 24, 57, 'Tutorial', 'en_US', 1);
INSERT INTO metadatavalue VALUES (254, 24, 57, 'Waterfall', 'en_US', 2);
INSERT INTO metadatavalue VALUES (255, 24, 28, 'Submitted by dspace dspace (dspace@example.com) on 2011-11-10T18:22:53Z
No. of bitstreams: 1
waterfall_tutorial.txt: 23 bytes, checksum: 5af3a08e06ae717b8ed9defdafd56e19 (MD5)', 'en', 1);
INSERT INTO metadatavalue VALUES (256, 24, 11, '2011-11-10T18:22:53Z', NULL, 1);
INSERT INTO metadatavalue VALUES (257, 24, 12, '2011-11-10T18:22:53Z', NULL, 1);
INSERT INTO metadatavalue VALUES (258, 24, 15, '2011-11-10T18:22:53Z', NULL, 1);
INSERT INTO metadatavalue VALUES (259, 24, 25, 'http://hdl.handle.net/123456789/33', NULL, 1);
INSERT INTO metadatavalue VALUES (260, 24, 28, 'Made available in DSpace on 2011-11-10T18:22:53Z (GMT). No. of bitstreams: 1
waterfall_tutorial.txt: 23 bytes, checksum: 5af3a08e06ae717b8ed9defdafd56e19 (MD5)', 'en', 2);


--
-- Data for Name: most_recent_checksum; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: registrationdata; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: resourcepolicy; Type: TABLE DATA; Schema: public; Owner: dspace
--

INSERT INTO resourcepolicy VALUES (1, 4, 1, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (2, 3, 1, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (3, 3, 1, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (4, 3, 1, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (5, 3, 2, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (6, 3, 2, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (7, 3, 2, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (8, 3, 2, 3, NULL, 2, NULL, NULL);
INSERT INTO resourcepolicy VALUES (9, 4, 2, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (10, 3, 3, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (11, 3, 3, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (12, 3, 3, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (13, 3, 3, 3, NULL, 3, NULL, NULL);
INSERT INTO resourcepolicy VALUES (14, 3, 4, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (15, 3, 4, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (16, 3, 4, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (17, 3, 4, 3, NULL, 4, NULL, NULL);
INSERT INTO resourcepolicy VALUES (18, 3, 5, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (19, 3, 5, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (20, 3, 5, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (21, 3, 5, 3, NULL, 5, NULL, NULL);
INSERT INTO resourcepolicy VALUES (22, 4, 3, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (23, 3, 6, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (24, 3, 6, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (25, 3, 6, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (26, 3, 6, 3, NULL, 6, NULL, NULL);
INSERT INTO resourcepolicy VALUES (27, 3, 7, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (28, 3, 7, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (29, 3, 7, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (30, 3, 7, 3, NULL, 7, NULL, NULL);
INSERT INTO resourcepolicy VALUES (31, 3, 8, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (32, 3, 8, 10, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (33, 3, 8, 9, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (130, 2, 4, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (34, 3, 8, 3, NULL, 8, NULL, NULL);
INSERT INTO resourcepolicy VALUES (240, 2, 8, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (131, 0, 7, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (290, 2, 10, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (132, 1, 7, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (241, 0, 17, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (133, 0, 8, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (345, 2, 12, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (80, 2, 2, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (134, 1, 8, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (81, 0, 3, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (242, 1, 15, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (82, 1, 3, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (291, 0, 21, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (83, 0, 4, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (243, 0, 18, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (84, 1, 4, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (395, 2, 14, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (244, 1, 16, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (292, 1, 19, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (346, 0, 26, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (293, 0, 22, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (449, 2, 17, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (294, 1, 20, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (347, 1, 23, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (396, 0, 30, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (184, 2, 6, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (348, 0, 27, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (185, 0, 11, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (499, 2, 19, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (186, 0, 12, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (349, 1, 24, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (187, 1, 11, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (397, 1, 27, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (188, 0, 13, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (450, 0, 34, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (189, 1, 12, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (398, 0, 31, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (553, 2, 22, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (399, 1, 28, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (451, 1, 31, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (500, 0, 38, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (55, 2, 1, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (452, 0, 35, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (56, 0, 1, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (57, 1, 1, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (453, 1, 32, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (58, 0, 2, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (501, 1, 35, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (59, 1, 2, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (554, 0, 42, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (502, 0, 39, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (503, 1, 36, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (555, 1, 39, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (556, 0, 43, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (557, 1, 40, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (155, 2, 5, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (156, 0, 9, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (105, 2, 3, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (106, 0, 5, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (157, 1, 9, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (107, 1, 5, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (108, 0, 6, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (158, 0, 10, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (109, 1, 6, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (159, 1, 10, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (265, 2, 9, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (266, 0, 19, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (267, 1, 17, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (268, 0, 20, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (269, 1, 18, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (370, 2, 13, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (371, 0, 28, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (372, 1, 25, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (214, 2, 7, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (215, 0, 14, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (373, 0, 29, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (216, 0, 15, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (319, 2, 11, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (217, 1, 13, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (218, 0, 16, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (320, 0, 23, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (219, 1, 14, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (374, 1, 26, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (321, 0, 24, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (322, 1, 21, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (323, 0, 25, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (324, 1, 22, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (474, 2, 18, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (475, 0, 36, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (476, 1, 33, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (477, 0, 37, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (424, 2, 16, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (425, 0, 32, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (478, 1, 34, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (426, 1, 29, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (427, 0, 33, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (428, 1, 30, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (528, 2, 21, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (529, 0, 40, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (603, 2, 24, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (530, 1, 37, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (531, 0, 41, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (604, 0, 46, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (532, 1, 38, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (605, 1, 43, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (606, 0, 47, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (578, 2, 23, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (607, 1, 44, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (579, 0, 44, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (580, 1, 41, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (581, 0, 45, 0, NULL, 0, NULL, NULL);
INSERT INTO resourcepolicy VALUES (582, 1, 42, 0, NULL, 0, NULL, NULL);


--
-- Data for Name: subscription; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: tasklistitem; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: workflowitem; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Data for Name: workspaceitem; Type: TABLE DATA; Schema: public; Owner: dspace
--



--
-- Name: bi_2_dis_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_2_dis
    ADD CONSTRAINT bi_2_dis_pkey PRIMARY KEY (id);


--
-- Name: bi_2_dmap_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_2_dmap
    ADD CONSTRAINT bi_2_dmap_pkey PRIMARY KEY (map_id);


--
-- Name: bi_4_dis_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_4_dis
    ADD CONSTRAINT bi_4_dis_pkey PRIMARY KEY (id);


--
-- Name: bi_4_dmap_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_4_dmap
    ADD CONSTRAINT bi_4_dmap_pkey PRIMARY KEY (map_id);


--
-- Name: bi_item_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_item
    ADD CONSTRAINT bi_item_pkey PRIMARY KEY (id);


--
-- Name: bi_withdrawn_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bi_withdrawn
    ADD CONSTRAINT bi_withdrawn_pkey PRIMARY KEY (id);


--
-- Name: bitstream_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bitstream
    ADD CONSTRAINT bitstream_pkey PRIMARY KEY (bitstream_id);


--
-- Name: bitstreamformatregistry_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bitstreamformatregistry
    ADD CONSTRAINT bitstreamformatregistry_pkey PRIMARY KEY (bitstream_format_id);


--
-- Name: bitstreamformatregistry_short_description_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bitstreamformatregistry
    ADD CONSTRAINT bitstreamformatregistry_short_description_key UNIQUE (short_description);


--
-- Name: bundle2bitstream_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bundle2bitstream
    ADD CONSTRAINT bundle2bitstream_pkey PRIMARY KEY (id);


--
-- Name: bundle_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY bundle
    ADD CONSTRAINT bundle_pkey PRIMARY KEY (bundle_id);


--
-- Name: checksum_history_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY checksum_history
    ADD CONSTRAINT checksum_history_pkey PRIMARY KEY (check_id);


--
-- Name: checksum_results_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY checksum_results
    ADD CONSTRAINT checksum_results_pkey PRIMARY KEY (result_code);


--
-- Name: collection2item_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY collection2item
    ADD CONSTRAINT collection2item_pkey PRIMARY KEY (id);


--
-- Name: collection_item_count_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY collection_item_count
    ADD CONSTRAINT collection_item_count_pkey PRIMARY KEY (collection_id);


--
-- Name: collection_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_pkey PRIMARY KEY (collection_id);


--
-- Name: communities2item_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY communities2item
    ADD CONSTRAINT communities2item_pkey PRIMARY KEY (id);


--
-- Name: community2collection_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY community2collection
    ADD CONSTRAINT community2collection_pkey PRIMARY KEY (id);


--
-- Name: community2community_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY community2community
    ADD CONSTRAINT community2community_pkey PRIMARY KEY (id);


--
-- Name: community_item_count_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY community_item_count
    ADD CONSTRAINT community_item_count_pkey PRIMARY KEY (community_id);


--
-- Name: community_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY community
    ADD CONSTRAINT community_pkey PRIMARY KEY (community_id);


--
-- Name: eperson_email_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY eperson
    ADD CONSTRAINT eperson_email_key UNIQUE (email);


--
-- Name: eperson_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY eperson
    ADD CONSTRAINT eperson_pkey PRIMARY KEY (eperson_id);


--
-- Name: epersongroup2eperson_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY epersongroup2eperson
    ADD CONSTRAINT epersongroup2eperson_pkey PRIMARY KEY (id);


--
-- Name: epersongroup2item_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY epersongroup2workspaceitem
    ADD CONSTRAINT epersongroup2item_pkey PRIMARY KEY (id);


--
-- Name: epersongroup_name_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY epersongroup
    ADD CONSTRAINT epersongroup_name_key UNIQUE (name);


--
-- Name: epersongroup_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY epersongroup
    ADD CONSTRAINT epersongroup_pkey PRIMARY KEY (eperson_group_id);


--
-- Name: fileextension_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY fileextension
    ADD CONSTRAINT fileextension_pkey PRIMARY KEY (file_extension_id);


--
-- Name: group2group_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY group2group
    ADD CONSTRAINT group2group_pkey PRIMARY KEY (id);


--
-- Name: group2groupcache_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY group2groupcache
    ADD CONSTRAINT group2groupcache_pkey PRIMARY KEY (id);


--
-- Name: handle_handle_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY handle
    ADD CONSTRAINT handle_handle_key UNIQUE (handle);


--
-- Name: handle_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY handle
    ADD CONSTRAINT handle_pkey PRIMARY KEY (handle_id);


--
-- Name: item2bundle_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY item2bundle
    ADD CONSTRAINT item2bundle_pkey PRIMARY KEY (id);


--
-- Name: item_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (item_id);


--
-- Name: metadatafieldregistry_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY metadatafieldregistry
    ADD CONSTRAINT metadatafieldregistry_pkey PRIMARY KEY (metadata_field_id);


--
-- Name: metadataschemaregistry_namespace_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY metadataschemaregistry
    ADD CONSTRAINT metadataschemaregistry_namespace_key UNIQUE (namespace);


--
-- Name: metadataschemaregistry_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY metadataschemaregistry
    ADD CONSTRAINT metadataschemaregistry_pkey PRIMARY KEY (metadata_schema_id);


--
-- Name: metadataschemaregistry_short_id_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY metadataschemaregistry
    ADD CONSTRAINT metadataschemaregistry_short_id_key UNIQUE (short_id);


--
-- Name: metadatavalue_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY metadatavalue
    ADD CONSTRAINT metadatavalue_pkey PRIMARY KEY (metadata_value_id);


--
-- Name: most_recent_checksum_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY most_recent_checksum
    ADD CONSTRAINT most_recent_checksum_pkey PRIMARY KEY (bitstream_id);


--
-- Name: registrationdata_email_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY registrationdata
    ADD CONSTRAINT registrationdata_email_key UNIQUE (email);


--
-- Name: registrationdata_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY registrationdata
    ADD CONSTRAINT registrationdata_pkey PRIMARY KEY (registrationdata_id);


--
-- Name: resourcepolicy_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY resourcepolicy
    ADD CONSTRAINT resourcepolicy_pkey PRIMARY KEY (policy_id);


--
-- Name: subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_pkey PRIMARY KEY (subscription_id);


--
-- Name: tasklistitem_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY tasklistitem
    ADD CONSTRAINT tasklistitem_pkey PRIMARY KEY (tasklist_id);


--
-- Name: workflowitem_item_id_key; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY workflowitem
    ADD CONSTRAINT workflowitem_item_id_key UNIQUE (item_id);


--
-- Name: workflowitem_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY workflowitem
    ADD CONSTRAINT workflowitem_pkey PRIMARY KEY (workflow_id);


--
-- Name: workspaceitem_pkey; Type: CONSTRAINT; Schema: public; Owner: dspace; Tablespace: 
--

ALTER TABLE ONLY workspaceitem
    ADD CONSTRAINT workspaceitem_pkey PRIMARY KEY (workspace_item_id);


--
-- Name: bi_2_dis_svalue_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_2_dis_svalue_idx ON bi_2_dis USING btree (sort_value);


--
-- Name: bi_2_dis_uvalue_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_2_dis_uvalue_idx ON bi_2_dis USING btree (upper(value));


--
-- Name: bi_2_dis_value_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_2_dis_value_idx ON bi_2_dis USING btree (value);


--
-- Name: bi_2_dmap_dist_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_2_dmap_dist_idx ON bi_2_dmap USING btree (distinct_id);


--
-- Name: bi_2_dmap_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_2_dmap_item_id_idx ON bi_2_dmap USING btree (item_id);


--
-- Name: bi_4_dis_svalue_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_4_dis_svalue_idx ON bi_4_dis USING btree (sort_value);


--
-- Name: bi_4_dis_uvalue_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_4_dis_uvalue_idx ON bi_4_dis USING btree (upper(value));


--
-- Name: bi_4_dis_value_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_4_dis_value_idx ON bi_4_dis USING btree (value);


--
-- Name: bi_4_dmap_dist_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_4_dmap_dist_idx ON bi_4_dmap USING btree (distinct_id);


--
-- Name: bi_4_dmap_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_4_dmap_item_id_idx ON bi_4_dmap USING btree (item_id);


--
-- Name: bi_item_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_item_item_id_idx ON bi_item USING btree (item_id);


--
-- Name: bi_item_s1_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_item_s1_idx ON bi_item USING btree (sort_1);


--
-- Name: bi_item_s2_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_item_s2_idx ON bi_item USING btree (sort_2);


--
-- Name: bi_item_s3_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_item_s3_idx ON bi_item USING btree (sort_3);


--
-- Name: bi_withdrawn_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_withdrawn_item_id_idx ON bi_withdrawn USING btree (item_id);


--
-- Name: bi_withdrawn_s1_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_withdrawn_s1_idx ON bi_withdrawn USING btree (sort_1);


--
-- Name: bi_withdrawn_s2_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_withdrawn_s2_idx ON bi_withdrawn USING btree (sort_2);


--
-- Name: bi_withdrawn_s3_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bi_withdrawn_s3_idx ON bi_withdrawn USING btree (sort_3);


--
-- Name: bit_bitstream_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bit_bitstream_fk_idx ON bitstream USING btree (bitstream_format_id);


--
-- Name: bundle2bitstream_bitstream_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bundle2bitstream_bitstream_fk_idx ON bundle2bitstream USING btree (bitstream_id);


--
-- Name: bundle2bitstream_bundle_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bundle2bitstream_bundle_idx ON bundle2bitstream USING btree (bundle_id);


--
-- Name: bundle_primary_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX bundle_primary_fk_idx ON bundle USING btree (primary_bitstream_id);


--
-- Name: ch_result_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX ch_result_fk_idx ON checksum_history USING btree (result);


--
-- Name: collection2item_collection_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection2item_collection_idx ON collection2item USING btree (collection_id);


--
-- Name: collection2item_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection2item_item_id_idx ON collection2item USING btree (item_id);


--
-- Name: collection_admin_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_admin_fk_idx ON collection USING btree (admin);


--
-- Name: collection_logo_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_logo_fk_idx ON collection USING btree (logo_bitstream_id);


--
-- Name: collection_submitter_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_submitter_fk_idx ON collection USING btree (submitter);


--
-- Name: collection_template_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_template_fk_idx ON collection USING btree (template_item_id);


--
-- Name: collection_workflow1_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_workflow1_fk_idx ON collection USING btree (workflow_step_1);


--
-- Name: collection_workflow2_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_workflow2_fk_idx ON collection USING btree (workflow_step_2);


--
-- Name: collection_workflow3_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX collection_workflow3_fk_idx ON collection USING btree (workflow_step_3);


--
-- Name: com2com_child_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX com2com_child_fk_idx ON community2community USING btree (child_comm_id);


--
-- Name: com2com_parent_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX com2com_parent_fk_idx ON community2community USING btree (parent_comm_id);


--
-- Name: comm2item_community_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX comm2item_community_fk_idx ON communities2item USING btree (community_id);


--
-- Name: communities2item_item_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX communities2item_item_id_idx ON communities2item USING btree (item_id);


--
-- Name: community2collection_collection_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX community2collection_collection_id_idx ON community2collection USING btree (collection_id);


--
-- Name: community2collection_community_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX community2collection_community_id_idx ON community2collection USING btree (community_id);


--
-- Name: community_logo_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX community_logo_fk_idx ON community USING btree (logo_bitstream_id);


--
-- Name: eperson_email_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX eperson_email_idx ON eperson USING btree (email);


--
-- Name: eperson_netid_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX eperson_netid_idx ON eperson USING btree (netid);


--
-- Name: epersongroup2eperson_group_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX epersongroup2eperson_group_idx ON epersongroup2eperson USING btree (eperson_group_id);


--
-- Name: epg2ep_eperson_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX epg2ep_eperson_fk_idx ON epersongroup2eperson USING btree (eperson_id);


--
-- Name: epg2wi_group_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX epg2wi_group_fk_idx ON epersongroup2workspaceitem USING btree (eperson_group_id);


--
-- Name: epg2wi_workspace_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX epg2wi_workspace_fk_idx ON epersongroup2workspaceitem USING btree (workspace_item_id);


--
-- Name: fe_bitstream_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX fe_bitstream_fk_idx ON fileextension USING btree (bitstream_format_id);


--
-- Name: g2g_child_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX g2g_child_fk_idx ON group2group USING btree (child_id);


--
-- Name: g2g_parent_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX g2g_parent_fk_idx ON group2group USING btree (parent_id);


--
-- Name: g2gc_child_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX g2gc_child_fk_idx ON group2group USING btree (child_id);


--
-- Name: g2gc_parent_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX g2gc_parent_fk_idx ON group2group USING btree (parent_id);


--
-- Name: handle_handle_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX handle_handle_idx ON handle USING btree (handle);


--
-- Name: handle_resource_id_and_type_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX handle_resource_id_and_type_idx ON handle USING btree (resource_id, resource_type_id);


--
-- Name: item2bundle_bundle_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX item2bundle_bundle_fk_idx ON item2bundle USING btree (bundle_id);


--
-- Name: item2bundle_item_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX item2bundle_item_idx ON item2bundle USING btree (item_id);


--
-- Name: item_submitter_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX item_submitter_fk_idx ON item USING btree (submitter_id);


--
-- Name: metadatafield_schema_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX metadatafield_schema_idx ON metadatafieldregistry USING btree (metadata_schema_id);


--
-- Name: metadatavalue_field_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX metadatavalue_field_fk_idx ON metadatavalue USING btree (metadata_field_id);


--
-- Name: metadatavalue_item_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX metadatavalue_item_idx ON metadatavalue USING btree (item_id);


--
-- Name: metadatavalue_item_idx2; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX metadatavalue_item_idx2 ON metadatavalue USING btree (item_id, metadata_field_id);


--
-- Name: mrc_result_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX mrc_result_fk_idx ON most_recent_checksum USING btree (result);


--
-- Name: resourcepolicy_type_id_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX resourcepolicy_type_id_idx ON resourcepolicy USING btree (resource_type_id, resource_id);


--
-- Name: rp_eperson_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX rp_eperson_fk_idx ON resourcepolicy USING btree (eperson_id);


--
-- Name: rp_epersongroup_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX rp_epersongroup_fk_idx ON resourcepolicy USING btree (epersongroup_id);


--
-- Name: subs_collection_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX subs_collection_fk_idx ON subscription USING btree (collection_id);


--
-- Name: subs_eperson_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX subs_eperson_fk_idx ON subscription USING btree (eperson_id);


--
-- Name: tasklist_eperson_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX tasklist_eperson_fk_idx ON tasklistitem USING btree (eperson_id);


--
-- Name: tasklist_workflow_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX tasklist_workflow_fk_idx ON tasklistitem USING btree (workflow_id);


--
-- Name: workflow_coll_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX workflow_coll_fk_idx ON workflowitem USING btree (collection_id);


--
-- Name: workflow_item_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX workflow_item_fk_idx ON workflowitem USING btree (item_id);


--
-- Name: workflow_owner_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX workflow_owner_fk_idx ON workflowitem USING btree (owner);


--
-- Name: workspace_coll_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX workspace_coll_fk_idx ON workspaceitem USING btree (collection_id);


--
-- Name: workspace_item_fk_idx; Type: INDEX; Schema: public; Owner: dspace; Tablespace: 
--

CREATE INDEX workspace_item_fk_idx ON workspaceitem USING btree (item_id);


--
-- Name: bi_2_dmap_distinct_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_2_dmap
    ADD CONSTRAINT bi_2_dmap_distinct_id_fkey FOREIGN KEY (distinct_id) REFERENCES bi_2_dis(id);


--
-- Name: bi_2_dmap_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_2_dmap
    ADD CONSTRAINT bi_2_dmap_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: bi_4_dmap_distinct_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_4_dmap
    ADD CONSTRAINT bi_4_dmap_distinct_id_fkey FOREIGN KEY (distinct_id) REFERENCES bi_4_dis(id);


--
-- Name: bi_4_dmap_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_4_dmap
    ADD CONSTRAINT bi_4_dmap_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: bi_item_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_item
    ADD CONSTRAINT bi_item_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: bi_withdrawn_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bi_withdrawn
    ADD CONSTRAINT bi_withdrawn_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: bitstream_bitstream_format_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bitstream
    ADD CONSTRAINT bitstream_bitstream_format_id_fkey FOREIGN KEY (bitstream_format_id) REFERENCES bitstreamformatregistry(bitstream_format_id);


--
-- Name: bundle2bitstream_bitstream_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bundle2bitstream
    ADD CONSTRAINT bundle2bitstream_bitstream_id_fkey FOREIGN KEY (bitstream_id) REFERENCES bitstream(bitstream_id);


--
-- Name: bundle2bitstream_bundle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bundle2bitstream
    ADD CONSTRAINT bundle2bitstream_bundle_id_fkey FOREIGN KEY (bundle_id) REFERENCES bundle(bundle_id);


--
-- Name: bundle_primary_bitstream_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY bundle
    ADD CONSTRAINT bundle_primary_bitstream_id_fkey FOREIGN KEY (primary_bitstream_id) REFERENCES bitstream(bitstream_id);


--
-- Name: checksum_history_result_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY checksum_history
    ADD CONSTRAINT checksum_history_result_fkey FOREIGN KEY (result) REFERENCES checksum_results(result_code);


--
-- Name: collection2item_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection2item
    ADD CONSTRAINT collection2item_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: collection2item_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection2item
    ADD CONSTRAINT collection2item_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: collection_admin_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_admin_fkey FOREIGN KEY (admin) REFERENCES epersongroup(eperson_group_id);


--
-- Name: collection_item_count_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection_item_count
    ADD CONSTRAINT collection_item_count_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: collection_logo_bitstream_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_logo_bitstream_id_fkey FOREIGN KEY (logo_bitstream_id) REFERENCES bitstream(bitstream_id);


--
-- Name: collection_submitter_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_submitter_fkey FOREIGN KEY (submitter) REFERENCES epersongroup(eperson_group_id);


--
-- Name: collection_template_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_template_item_id_fkey FOREIGN KEY (template_item_id) REFERENCES item(item_id);


--
-- Name: collection_workflow_step_1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_workflow_step_1_fkey FOREIGN KEY (workflow_step_1) REFERENCES epersongroup(eperson_group_id);


--
-- Name: collection_workflow_step_2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_workflow_step_2_fkey FOREIGN KEY (workflow_step_2) REFERENCES epersongroup(eperson_group_id);


--
-- Name: collection_workflow_step_3_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY collection
    ADD CONSTRAINT collection_workflow_step_3_fkey FOREIGN KEY (workflow_step_3) REFERENCES epersongroup(eperson_group_id);


--
-- Name: communities2item_community_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY communities2item
    ADD CONSTRAINT communities2item_community_id_fkey FOREIGN KEY (community_id) REFERENCES community(community_id);


--
-- Name: communities2item_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY communities2item
    ADD CONSTRAINT communities2item_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: community2collection_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community2collection
    ADD CONSTRAINT community2collection_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: community2collection_community_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community2collection
    ADD CONSTRAINT community2collection_community_id_fkey FOREIGN KEY (community_id) REFERENCES community(community_id);


--
-- Name: community2community_child_comm_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community2community
    ADD CONSTRAINT community2community_child_comm_id_fkey FOREIGN KEY (child_comm_id) REFERENCES community(community_id);


--
-- Name: community2community_parent_comm_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community2community
    ADD CONSTRAINT community2community_parent_comm_id_fkey FOREIGN KEY (parent_comm_id) REFERENCES community(community_id);


--
-- Name: community_item_count_community_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community_item_count
    ADD CONSTRAINT community_item_count_community_id_fkey FOREIGN KEY (community_id) REFERENCES community(community_id);


--
-- Name: community_logo_bitstream_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY community
    ADD CONSTRAINT community_logo_bitstream_id_fkey FOREIGN KEY (logo_bitstream_id) REFERENCES bitstream(bitstream_id);


--
-- Name: epersongroup2eperson_eperson_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY epersongroup2eperson
    ADD CONSTRAINT epersongroup2eperson_eperson_group_id_fkey FOREIGN KEY (eperson_group_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: epersongroup2eperson_eperson_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY epersongroup2eperson
    ADD CONSTRAINT epersongroup2eperson_eperson_id_fkey FOREIGN KEY (eperson_id) REFERENCES eperson(eperson_id);


--
-- Name: epersongroup2workspaceitem_eperson_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY epersongroup2workspaceitem
    ADD CONSTRAINT epersongroup2workspaceitem_eperson_group_id_fkey FOREIGN KEY (eperson_group_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: epersongroup2workspaceitem_workspace_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY epersongroup2workspaceitem
    ADD CONSTRAINT epersongroup2workspaceitem_workspace_item_id_fkey FOREIGN KEY (workspace_item_id) REFERENCES workspaceitem(workspace_item_id);


--
-- Name: fileextension_bitstream_format_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY fileextension
    ADD CONSTRAINT fileextension_bitstream_format_id_fkey FOREIGN KEY (bitstream_format_id) REFERENCES bitstreamformatregistry(bitstream_format_id);


--
-- Name: group2group_child_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY group2group
    ADD CONSTRAINT group2group_child_id_fkey FOREIGN KEY (child_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: group2group_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY group2group
    ADD CONSTRAINT group2group_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: group2groupcache_child_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY group2groupcache
    ADD CONSTRAINT group2groupcache_child_id_fkey FOREIGN KEY (child_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: group2groupcache_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY group2groupcache
    ADD CONSTRAINT group2groupcache_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: item2bundle_bundle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY item2bundle
    ADD CONSTRAINT item2bundle_bundle_id_fkey FOREIGN KEY (bundle_id) REFERENCES bundle(bundle_id);


--
-- Name: item2bundle_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY item2bundle
    ADD CONSTRAINT item2bundle_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: item_submitter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_submitter_id_fkey FOREIGN KEY (submitter_id) REFERENCES eperson(eperson_id);


--
-- Name: metadatafieldregistry_metadata_schema_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY metadatafieldregistry
    ADD CONSTRAINT metadatafieldregistry_metadata_schema_id_fkey FOREIGN KEY (metadata_schema_id) REFERENCES metadataschemaregistry(metadata_schema_id);


--
-- Name: metadatavalue_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY metadatavalue
    ADD CONSTRAINT metadatavalue_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: metadatavalue_metadata_field_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY metadatavalue
    ADD CONSTRAINT metadatavalue_metadata_field_id_fkey FOREIGN KEY (metadata_field_id) REFERENCES metadatafieldregistry(metadata_field_id);


--
-- Name: most_recent_checksum_bitstream_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY most_recent_checksum
    ADD CONSTRAINT most_recent_checksum_bitstream_id_fkey FOREIGN KEY (bitstream_id) REFERENCES bitstream(bitstream_id);


--
-- Name: most_recent_checksum_result_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY most_recent_checksum
    ADD CONSTRAINT most_recent_checksum_result_fkey FOREIGN KEY (result) REFERENCES checksum_results(result_code);


--
-- Name: resourcepolicy_eperson_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY resourcepolicy
    ADD CONSTRAINT resourcepolicy_eperson_id_fkey FOREIGN KEY (eperson_id) REFERENCES eperson(eperson_id);


--
-- Name: resourcepolicy_epersongroup_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY resourcepolicy
    ADD CONSTRAINT resourcepolicy_epersongroup_id_fkey FOREIGN KEY (epersongroup_id) REFERENCES epersongroup(eperson_group_id);


--
-- Name: subscription_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: subscription_eperson_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_eperson_id_fkey FOREIGN KEY (eperson_id) REFERENCES eperson(eperson_id);


--
-- Name: tasklistitem_eperson_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY tasklistitem
    ADD CONSTRAINT tasklistitem_eperson_id_fkey FOREIGN KEY (eperson_id) REFERENCES eperson(eperson_id);


--
-- Name: tasklistitem_workflow_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY tasklistitem
    ADD CONSTRAINT tasklistitem_workflow_id_fkey FOREIGN KEY (workflow_id) REFERENCES workflowitem(workflow_id);


--
-- Name: workflowitem_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY workflowitem
    ADD CONSTRAINT workflowitem_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: workflowitem_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY workflowitem
    ADD CONSTRAINT workflowitem_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);


--
-- Name: workflowitem_owner_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY workflowitem
    ADD CONSTRAINT workflowitem_owner_fkey FOREIGN KEY (owner) REFERENCES eperson(eperson_id);


--
-- Name: workspaceitem_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY workspaceitem
    ADD CONSTRAINT workspaceitem_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES collection(collection_id);


--
-- Name: workspaceitem_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dspace
--

ALTER TABLE ONLY workspaceitem
    ADD CONSTRAINT workspaceitem_item_id_fkey FOREIGN KEY (item_id) REFERENCES item(item_id);



--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--



--
-- PostgreSQL database dump complete
--

