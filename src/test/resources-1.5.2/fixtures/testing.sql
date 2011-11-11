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

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET SESSION AUTHORIZATION 'dspace-integ';

SET search_path = public, pg_catalog;

--
-- Name: getnextid(character varying); Type: FUNCTION; Schema: public; Owner: dspace
--

CREATE FUNCTION getnextid(character varying) RETURNS integer
    LANGUAGE sql
    AS $_$SELECT CAST (nextval($1 || '_seq') AS INTEGER) AS RESULT $_$;


SET default_tablespace = '';

SET default_with_oids = false;

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

SELECT pg_catalog.setval('bi_2_dis_seq', 1, false);


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

SELECT pg_catalog.setval('bi_2_dmap_seq', 1, false);


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

SELECT pg_catalog.setval('bi_4_dis_seq', 1, false);


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

SELECT pg_catalog.setval('bi_4_dmap_seq', 1, false);


--
-- Name: bi_item; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_item (
    id integer NOT NULL,
    item_id integer,
    sort_3 text,
    sort_1 text,
    sort_2 text
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

SELECT pg_catalog.setval('bi_item_seq', 1, false);


--
-- Name: bi_withdrawn; Type: TABLE; Schema: public; Owner: dspace; Tablespace: 
--

CREATE TABLE bi_withdrawn (
    id integer NOT NULL,
    item_id integer,
    sort_3 text,
    sort_1 text,
    sort_2 text
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

SELECT pg_catalog.setval('bitstream_seq', 1, false);


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

SELECT pg_catalog.setval('bundle2bitstream_seq', 1, false);


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

SELECT pg_catalog.setval('bundle_seq', 1, false);


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

SELECT pg_catalog.setval('collection2item_seq', 1, false);


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

SELECT pg_catalog.setval('collection_seq', 1, false);


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

SELECT pg_catalog.setval('communities2item_seq', 1, false);


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

SELECT pg_catalog.setval('community2collection_seq', 1, false);


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

SELECT pg_catalog.setval('community_seq', 2, true);


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

SELECT pg_catalog.setval('metadatavalue_seq', 1, false);


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

SELECT pg_catalog.setval('epersongroup_seq', 1, true);


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

SELECT pg_catalog.setval('handle_seq', 2, true);


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

SELECT pg_catalog.setval('item2bundle_seq', 1, false);


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

SELECT pg_catalog.setval('item_seq', 1, false);


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

SELECT pg_catalog.setval('resourcepolicy_seq', 2, true);


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

SELECT pg_catalog.setval('workflowitem_seq', 1, false);


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

SELECT pg_catalog.setval('workspaceitem_seq', 1, false);


--
-- Name: check_id; Type: DEFAULT; Schema: public; Owner: dspace
--

ALTER TABLE checksum_history ALTER COLUMN check_id SET DEFAULT nextval('checksum_history_check_id_seq'::regclass);


--
-- Data for Name: bi_2_dis; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_2_dis (id, value, sort_value) FROM stdin;
\.


--
-- Data for Name: bi_2_dmap; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_2_dmap (map_id, item_id, distinct_id) FROM stdin;
\.


--
-- Data for Name: bi_4_dis; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_4_dis (id, value, sort_value) FROM stdin;
\.


--
-- Data for Name: bi_4_dmap; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_4_dmap (map_id, item_id, distinct_id) FROM stdin;
\.


--
-- Data for Name: bi_item; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_item (id, item_id, sort_3, sort_1, sort_2) FROM stdin;
\.


--
-- Data for Name: bi_withdrawn; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bi_withdrawn (id, item_id, sort_3, sort_1, sort_2) FROM stdin;
\.


--
-- Data for Name: bitstream; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bitstream (bitstream_id, bitstream_format_id, name, size_bytes, checksum, checksum_algorithm, description, user_format_description, source, internal_id, deleted, store_number, sequence_id) FROM stdin;
\.


--
-- Data for Name: bitstreamformatregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bitstreamformatregistry (bitstream_format_id, mimetype, short_description, description, support_level, internal) FROM stdin;
1	application/octet-stream	Unknown	Unknown data format	0	f
2	text/plain; charset=utf-8	License	Item-specific license agreed upon to submission	1	t
3	application/pdf	Adobe PDF	Adobe Portable Document Format	1	f
4	text/xml	XML	Extensible Markup Language	1	f
5	text/plain	Text	Plain Text	1	f
6	text/html	HTML	Hypertext Markup Language	1	f
7	text/css	CSS	Cascading Style Sheets	1	f
8	application/msword	Microsoft Word	Microsoft Word	1	f
9	application/vnd.openxmlformats-officedocument.wordprocessingml.document	Microsoft Word XML	Microsoft Word XML	1	f
10	application/vnd.ms-powerpoint	Microsoft Powerpoint	Microsoft Powerpoint	1	f
11	application/vnd.openxmlformats-officedocument.presentationml.presentation	Microsoft Powerpoint XML	Microsoft Powerpoint XML	1	f
12	application/vnd.ms-excel	Microsoft Excel	Microsoft Excel	1	f
13	application/vnd.openxmlformats-officedocument.spreadsheetml.sheet	Microsoft Excel XML	Microsoft Excel XML	1	f
14	application/marc	MARC	Machine-Readable Cataloging records	1	f
15	image/jpeg	JPEG	Joint Photographic Experts Group/JPEG File Interchange Format (JFIF)	1	f
16	image/gif	GIF	Graphics Interchange Format	1	f
17	image/png	image/png	Portable Network Graphics	1	f
18	image/tiff	TIFF	Tag Image File Format	1	f
19	audio/x-aiff	AIFF	Audio Interchange File Format	1	f
20	audio/basic	audio/basic	Basic Audio	1	f
21	audio/x-wav	WAV	Broadcase Wave Format	1	f
22	video/mpeg	MPEG	Moving Picture Experts Group	1	f
23	text/richtext	RTF	Rich Text Format	1	f
24	application/vnd.visio	Microsoft Visio	Microsoft Visio	1	f
25	application/x-filemaker	FMP3	Filemaker Pro	1	f
26	image/x-ms-bmp	BMP	Microsoft Windows bitmap	1	f
27	application/x-photoshop	Photoshop	Photoshop	1	f
28	application/postscript	Postscript	Postscript Files	1	f
29	video/quicktime	Video Quicktime	Video Quicktime	1	f
30	audio/x-mpeg	MPEG Audio	MPEG Audio	1	f
31	application/vnd.ms-project	Microsoft Project	Microsoft Project	1	f
32	application/mathematica	Mathematica	Mathematica Notebook	1	f
33	application/x-latex	LateX	LaTeX document	1	f
34	application/x-tex	TeX	Tex/LateX document	1	f
35	application/x-dvi	TeX dvi	TeX dvi format	1	f
36	application/sgml	SGML	SGML application (RFC 1874)	1	f
37	application/wordperfect5.1	WordPerfect	WordPerfect 5.1 document	1	f
38	audio/x-pn-realaudio	RealAudio	RealAudio file	1	f
39	image/x-photo-cd	Photo CD	Kodak Photo CD image	1	f
40	application/vnd.oasis.opendocument.text	OpenDocument Text	OpenDocument Text	1	f
41	application/vnd.oasis.opendocument.text-template	OpenDocument Text Template	OpenDocument Text Template	1	f
42	application/vnd.oasis.opendocument.text-web	OpenDocument HTML Template	OpenDocument HTML Template	1	f
43	application/vnd.oasis.opendocument.text-master	OpenDocument Master Document	OpenDocument Master Document	1	f
44	application/vnd.oasis.opendocument.graphics	OpenDocument Drawing	OpenDocument Drawing	1	f
45	application/vnd.oasis.opendocument.graphics-template	OpenDocument Drawing Template	OpenDocument Drawing Template	1	f
46	application/vnd.oasis.opendocument.presentation	OpenDocument Presentation	OpenDocument Presentation	1	f
47	application/vnd.oasis.opendocument.presentation-template	OpenDocument Presentation Template	OpenDocument Presentation Template	1	f
48	application/vnd.oasis.opendocument.spreadsheet	OpenDocument Spreadsheet	OpenDocument Spreadsheet	1	f
49	application/vnd.oasis.opendocument.spreadsheet-template	OpenDocument Spreadsheet Template	OpenDocument Spreadsheet Template	1	f
50	application/vnd.oasis.opendocument.chart	OpenDocument Chart	OpenDocument Chart	1	f
51	application/vnd.oasis.opendocument.formula	OpenDocument Formula	OpenDocument Formula	1	f
52	application/vnd.oasis.opendocument.database	OpenDocument Database	OpenDocument Database	1	f
53	application/vnd.oasis.opendocument.image	OpenDocument Image	OpenDocument Image	1	f
54	application/vnd.openofficeorg.extension	OpenOffice.org extension	OpenOffice.org extension (since OOo 2.1)	1	f
55	application/vnd.sun.xml.writer	Writer 6.0 documents	Writer 6.0 documents	1	f
56	application/vnd.sun.xml.writer.template	Writer 6.0 templates	Writer 6.0 templates	1	f
57	application/vnd.sun.xml.calc	Calc 6.0 spreadsheets	Calc 6.0 spreadsheets	1	f
58	application/vnd.sun.xml.calc.template	Calc 6.0 templates	Calc 6.0 templates	1	f
59	application/vnd.sun.xml.draw	Draw 6.0 documents	Draw 6.0 documents	1	f
60	application/vnd.sun.xml.draw.template	Draw 6.0 templates	Draw 6.0 templates	1	f
61	application/vnd.sun.xml.impress	Impress 6.0 presentations	Impress 6.0 presentations	1	f
62	application/vnd.sun.xml.impress.template	Impress 6.0 templates	Impress 6.0 templates	1	f
63	application/vnd.sun.xml.writer.global	Writer 6.0 global documents	Writer 6.0 global documents	1	f
64	application/vnd.sun.xml.math	Math 6.0 documents	Math 6.0 documents	1	f
65	application/vnd.stardivision.writer	StarWriter 5.x documents	StarWriter 5.x documents	1	f
66	application/vnd.stardivision.writer-global	StarWriter 5.x global documents	StarWriter 5.x global documents	1	f
67	application/vnd.stardivision.calc	StarCalc 5.x spreadsheets	StarCalc 5.x spreadsheets	1	f
68	application/vnd.stardivision.draw	StarDraw 5.x documents	StarDraw 5.x documents	1	f
69	application/vnd.stardivision.impress	StarImpress 5.x presentations	StarImpress 5.x presentations	1	f
70	application/vnd.stardivision.impress-packed	StarImpress Packed 5.x files	StarImpress Packed 5.x files	1	f
71	application/vnd.stardivision.math	StarMath 5.x documents	StarMath 5.x documents	1	f
72	application/vnd.stardivision.chart	StarChart 5.x documents	StarChart 5.x documents	1	f
73	application/vnd.stardivision.mail	StarMail 5.x mail files	StarMail 5.x mail files	1	f
\.


--
-- Data for Name: bundle; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bundle (bundle_id, name, primary_bitstream_id) FROM stdin;
\.


--
-- Data for Name: bundle2bitstream; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY bundle2bitstream (id, bundle_id, bitstream_id) FROM stdin;
\.


--
-- Data for Name: checksum_history; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY checksum_history (check_id, bitstream_id, process_start_date, process_end_date, checksum_expected, checksum_calculated, result) FROM stdin;
\.


--
-- Data for Name: checksum_results; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY checksum_results (result_code, result_description) FROM stdin;
INVALID_HISTORY	Install of the cheksum checking code do not consider this history as valid
BITSTREAM_NOT_FOUND	The bitstream could not be found
CHECKSUM_MATCH	Current checksum matched previous checksum
CHECKSUM_NO_MATCH	Current checksum does not match previous checksum
CHECKSUM_PREV_NOT_FOUND	Previous checksum was not found: no comparison possible
BITSTREAM_INFO_NOT_FOUND	Bitstream info not found
CHECKSUM_ALGORITHM_INVALID	Invalid checksum algorithm
BITSTREAM_NOT_PROCESSED	Bitstream marked to_be_processed=false
BITSTREAM_MARKED_DELETED	Bitstream marked deleted in bitstream table
\.


--
-- Data for Name: collection; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY collection (collection_id, name, short_description, introductory_text, logo_bitstream_id, template_item_id, provenance_description, license, copyright_text, side_bar_text, workflow_step_1, workflow_step_2, workflow_step_3, submitter, admin) FROM stdin;
\.


--
-- Data for Name: collection2item; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY collection2item (id, collection_id, item_id) FROM stdin;
\.


--
-- Data for Name: collection_item_count; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY collection_item_count (collection_id, count) FROM stdin;
\.


--
-- Data for Name: communities2item; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY communities2item (id, community_id, item_id) FROM stdin;
\.


--
-- Data for Name: community; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY community (community_id, name, short_description, introductory_text, logo_bitstream_id, copyright_text, side_bar_text) FROM stdin;
2	Community no 1	Short description of community no 1	Introductory text for community no 1	\N	Copyright information	Side bar text for community 1
\.


--
-- Data for Name: community2collection; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY community2collection (id, community_id, collection_id) FROM stdin;
\.


--
-- Data for Name: community2community; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY community2community (id, parent_comm_id, child_comm_id) FROM stdin;
\.


--
-- Data for Name: community_item_count; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY community_item_count (community_id, count) FROM stdin;
\.


--
-- Data for Name: eperson; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY eperson (eperson_id, email, password, firstname, lastname, can_log_in, require_certificate, self_registered, last_active, sub_frequency, phone, netid, language) FROM stdin;
1	dspace@example.com	d2a147310ed200c73a5978baebf60e19	dspace	dspace	t	f	f	\N	\N	\N	\N	en
\.


--
-- Data for Name: epersongroup; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY epersongroup (eperson_group_id, name) FROM stdin;
0	Anonymous
1	Administrator
\.


--
-- Data for Name: epersongroup2eperson; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY epersongroup2eperson (id, eperson_group_id, eperson_id) FROM stdin;
1	1	1
\.


--
-- Data for Name: epersongroup2workspaceitem; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY epersongroup2workspaceitem (id, eperson_group_id, workspace_item_id) FROM stdin;
\.


--
-- Data for Name: fileextension; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY fileextension (file_extension_id, bitstream_format_id, extension) FROM stdin;
1	3	pdf
2	4	xml
3	5	txt
4	5	asc
5	6	htm
6	6	html
7	7	css
8	8	doc
9	9	docx
10	10	ppt
11	11	pptx
12	12	xls
13	13	xlsx
14	15	jpeg
15	15	jpg
16	16	gif
17	17	png
18	18	tiff
19	18	tif
20	19	aiff
21	19	aif
22	19	aifc
23	20	au
24	20	snd
25	21	wav
26	22	mpeg
27	22	mpg
28	22	mpe
29	23	rtf
30	24	vsd
31	25	fm
32	26	bmp
33	27	psd
34	27	pdd
35	28	ps
36	28	eps
37	28	ai
38	29	mov
39	29	qt
40	30	mpa
41	30	abs
42	30	mpega
43	31	mpp
44	31	mpx
45	31	mpd
46	32	ma
47	33	latex
48	34	tex
49	35	dvi
50	36	sgm
51	36	sgml
52	37	wpd
53	38	ra
54	38	ram
55	39	pcd
56	40	odt
57	41	ott
58	42	oth
59	43	odm
60	44	odg
61	45	otg
62	46	odp
63	47	otp
64	48	ods
65	49	ots
66	50	odc
67	51	odf
68	52	odb
69	53	odi
70	54	oxt
71	55	sxw
72	56	stw
73	57	sxc
74	58	stc
75	59	sxd
76	60	std
77	61	sxi
78	62	sti
79	63	sxg
80	64	sxm
81	65	sdw
82	66	sgl
83	67	sdc
84	68	sda
85	69	sdd
86	70	sdp
87	71	smf
88	72	sds
89	73	sdm
\.


--
-- Data for Name: group2group; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY group2group (id, parent_id, child_id) FROM stdin;
\.


--
-- Data for Name: group2groupcache; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY group2groupcache (id, parent_id, child_id) FROM stdin;
\.


--
-- Data for Name: handle; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY handle (handle_id, handle, resource_type_id, resource_id) FROM stdin;
1	123456789/1	4	1
2	123456789/2	4	2
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY item (item_id, submitter_id, in_archive, withdrawn, last_modified, owning_collection) FROM stdin;
\.


--
-- Data for Name: item2bundle; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY item2bundle (id, item_id, bundle_id) FROM stdin;
\.


--
-- Data for Name: metadatafieldregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY metadatafieldregistry (metadata_field_id, metadata_schema_id, element, qualifier, scope_note) FROM stdin;
1	1	contributor	\N	A person, organization, or service responsible for the content of the resource.  Catch-all for unspecified contributors.
2	1	contributor	advisor	Use primarily for thesis advisor.
3	1	contributor	author	\N
4	1	contributor	editor	\N
5	1	contributor	illustrator	\N
6	1	contributor	other	\N
7	1	coverage	spatial	Spatial characteristics of content.
8	1	coverage	temporal	Temporal characteristics of content.
9	1	creator	\N	Do not use; only for harvested metadata.
10	1	date	\N	Use qualified form if possible.
11	1	date	accessioned	Date DSpace takes possession of item.
12	1	date	available	Date or date range item became available to the public.
13	1	date	copyright	Date of copyright.
14	1	date	created	Date of creation or manufacture of intellectual content if different from date.issued.
15	1	date	issued	Date of publication or distribution.
16	1	date	submitted	Recommend for theses/dissertations.
17	1	identifier	\N	Catch-all for unambiguous identifiers not defined by\n    qualified form; use identifier.other for a known identifier common\n    to a local collection instead of unqualified form.
18	1	identifier	citation	Human-readable, standard bibliographic citation \n    of non-DSpace format of this item
19	1	identifier	govdoc	A government document number
20	1	identifier	isbn	International Standard Book Number
21	1	identifier	issn	International Standard Serial Number
22	1	identifier	sici	Serial Item and Contribution Identifier
23	1	identifier	ismn	International Standard Music Number
24	1	identifier	other	A known identifier type common to a local collection.
25	1	identifier	uri	Uniform Resource Identifier
26	1	description	\N	Catch-all for any description not defined by qualifiers.
27	1	description	abstract	Abstract or summary.
28	1	description	provenance	The history of custody of the item since its creation, including any changes successive custodians made to it.
29	1	description	sponsorship	Information about sponsoring agencies, individuals, or\n    contractual arrangements for the item.
30	1	description	statementofresponsibility	To preserve statement of responsibility from MARC records.
31	1	description	tableofcontents	A table of contents for a given item.
32	1	description	uri	Uniform Resource Identifier pointing to description of\n    this item.
33	1	format	\N	Catch-all for any format information not defined by qualifiers.
34	1	format	extent	Size or duration.
35	1	format	medium	Physical medium.
36	1	format	mimetype	Registered MIME type identifiers.
37	1	language	\N	Catch-all for non-ISO forms of the language of the\n    item, accommodating harvested values.
38	1	language	iso	Current ISO standard for language of intellectual content, including country codes (e.g. "en_US").
39	1	publisher	\N	Entity responsible for publication, distribution, or imprint.
40	1	relation	\N	Catch-all for references to other related items.
41	1	relation	isformatof	References additional physical form.
42	1	relation	ispartof	References physically or logically containing item.
43	1	relation	ispartofseries	Series name and number within that series, if available.
44	1	relation	haspart	References physically or logically contained item.
45	1	relation	isversionof	References earlier version.
46	1	relation	hasversion	References later version.
47	1	relation	isbasedon	References source.
48	1	relation	isreferencedby	Pointed to by referenced resource.
49	1	relation	requires	Referenced resource is required to support function,\n    delivery, or coherence of item.
50	1	relation	replaces	References preceeding item.
51	1	relation	isreplacedby	References succeeding item.
52	1	relation	uri	References Uniform Resource Identifier for related item.
53	1	rights	\N	Terms governing use and reproduction.
54	1	rights	uri	References terms governing use and reproduction.
55	1	source	\N	Do not use; only for harvested metadata.
56	1	source	uri	Do not use; only for harvested metadata.
57	1	subject	\N	Uncontrolled index term.
58	1	subject	classification	Catch-all for value from local classification system;\n    global classification systems will receive specific qualifier
59	1	subject	ddc	Dewey Decimal Classification Number
60	1	subject	lcc	Library of Congress Classification Number
61	1	subject	lcsh	Library of Congress Subject Headings
62	1	subject	mesh	MEdical Subject Headings
63	1	subject	other	Local controlled vocabulary; global vocabularies will receive specific qualifier.
64	1	title	\N	Title statement/title proper.
65	1	title	alternative	Varying (or substitute) form of title proper appearing in item,\n    e.g. abbreviation or translation
66	1	type	\N	Nature or genre of content.
67	1	date	updated	The last time the item was updated via the SWORD interface
68	1	description	version	The Peer Reviewed status of an item
69	1	identifier	slug	a uri supplied via the sword slug header, as a suggested uri for the item
70	1	language	rfc3066	the rfc3066 form of the language for the item
71	1	rights	holder	The owner of the copyright
\.


--
-- Data for Name: metadataschemaregistry; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY metadataschemaregistry (metadata_schema_id, namespace, short_id) FROM stdin;
1	http://dublincore.org/documents/dcmi-terms/	dc
\.


--
-- Data for Name: metadatavalue; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY metadatavalue (metadata_value_id, item_id, metadata_field_id, text_value, text_lang, place) FROM stdin;
\.


--
-- Data for Name: most_recent_checksum; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY most_recent_checksum (bitstream_id, to_be_processed, expected_checksum, current_checksum, last_process_start_date, last_process_end_date, checksum_algorithm, matched_prev_checksum, result) FROM stdin;
\.


--
-- Data for Name: registrationdata; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY registrationdata (registrationdata_id, email, token, expires) FROM stdin;
\.


--
-- Data for Name: resourcepolicy; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY resourcepolicy (policy_id, resource_type_id, resource_id, action_id, eperson_id, epersongroup_id, start_date, end_date) FROM stdin;
2	4	2	0	\N	0	\N	\N
\.


--
-- Data for Name: subscription; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY subscription (subscription_id, eperson_id, collection_id) FROM stdin;
\.


--
-- Data for Name: tasklistitem; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY tasklistitem (tasklist_id, eperson_id, workflow_id) FROM stdin;
\.


--
-- Data for Name: workflowitem; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY workflowitem (workflow_id, item_id, collection_id, state, owner, multiple_titles, published_before, multiple_files) FROM stdin;
\.


--
-- Data for Name: workspaceitem; Type: TABLE DATA; Schema: public; Owner: dspace
--

COPY workspaceitem (workspace_item_id, item_id, collection_id, multiple_titles, published_before, multiple_files, stage_reached, page_reached) FROM stdin;
\.


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


SET SESSION AUTHORIZATION 'postgres';

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

