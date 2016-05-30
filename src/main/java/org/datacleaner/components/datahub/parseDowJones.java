package org.datacleaner.components.datahub;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.datacleaner.api.*;
import org.datacleaner.components.categories.ReferenceDataCategory;
import org.datacleaner.components.datahub.enums.RecordType;
import org.datacleaner.components.datahub.enums.SearchType;
import org.datacleaner.components.datahub.enums.identifyField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named("DowJones Parser")
@Alias("DowJones")
@Description("Parse the Dow Jones xml feed")
@Categorized(ReferenceDataCategory.class)


public class parseDowJones implements Transformer {


    @Inject
    @Configured("identifyFields")
    InputColumn<String>[] columns;
    @Inject
    @Configured(order = 2, required = true)
    @Description("RecordType")
    RecordType recordType;
    @Inject
    @Configured(order = 2, required = true)
    @Description("SearchType")
    SearchType searchType;
    @Inject
    @Configured(order = 4)
    @Description("maxRecords")
    String maxRecords;
    @Inject
    @Configured(order = 5)
    @Description("Minumun Score")
    String minScore;
    @Inject
    @Configured(order = 6)
    @Description("Potential Threshold")
    Integer potTreshold;

    @Configured(order = 1)
    @MappedProperty("identifyFields")
    identifyField[] mappedFields;
    @Inject
    @Provided
    OutputRowCollector outputRowCollector;

    @Override
    public OutputColumns getOutputColumns() {

        String[] columnNames = {"trafficlight", "matched_id",  "score"};
        Class<?>[] columnTypes = new Class[]{String.class, String.class, Integer.class};
        return new OutputColumns(columnNames, columnTypes);
    }

    @Override
    public Object[] transform(InputRow inputRow) {
        String output;
        Integer statuscode;
        StringEntity input;
        CloseableHttpClient httpClient;
        HttpPost httpPost;
        CloseableHttpResponse response;
        Object[] resultObj;

        httpClient = HttpClients.createDefault();

        if (Objects.equals(searchType.getName(), "GOLDEN_RECORDS")) {
            httpPost = new HttpPost("http://mdm-poc:8080/service/v1/search/goldenrecords");
        } else {
            httpPost = new HttpPost("http://mdm-poc:8080/service/v1/search/sourcerecords");
        }
        List<NameValuePair> nvps;
        nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json; charset=UTF8"));
        nvps.add(new BasicNameValuePair("CDI-userId", "cdidatasteward"));
        nvps.add(new BasicNameValuePair("CDI-ticket", ""));
        nvps.add(new BasicNameValuePair("CDI-serviceUrl", ""));

        try {
            String inputStr;
            inputStr = new SearchQuery(inputRow).invoke();
            String trafficlight;

            input= new StringEntity(inputStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(input);
            for (NameValuePair h : nvps) {
                httpPost.addHeader(h.getName(), h.getValue());
            }

            response = httpClient.execute(httpPost);

            statuscode = response.getStatusLine().getStatusCode();
            if (statuscode != 200) {
                trafficlight = "Grey";
                resultObj = new Object[]{trafficlight, null, null};
                outputRowCollector.putValues(resultObj);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            while ((output = br.readLine()) != null) {
                JSONObject json = null;
                try {
                    json = new JSONObject(output);
                    if (json.length() == 0) {
                        trafficlight = "Grey";
                        resultObj = new Object[]{trafficlight, null, null};
                        outputRowCollector.putValues(resultObj);
                    } else {

                        JSONObject recordList = json.getJSONObject(recordType.getName());


                        if (Objects.equals(searchType.getName(), "GOLDEN_RECORDS")) {
                            parseGoldenRecordResult(recordList);
                        } else {
                            parseSourceRecordResult(recordList);
                        }


                    }
                } catch (JSONException JSONexp) {

                    trafficlight = "Grey";
                    resultObj = new Object[]{trafficlight, JSONexp, json};
                    outputRowCollector.putValues(resultObj);
                }
            }
            response.close();
            httpClient.close();


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void parseSourceRecordResult(JSONObject recordList) throws JSONException {
        String trafficlight;
        String matched_gr;
        Integer score;
        Object[] resultObj;
        JSONArray sourceRecords;
        sourceRecords = recordList.getJSONArray("sourceRecords");

        if (sourceRecords.length() == 0) {
            trafficlight = "NoMatch";
            resultObj = new Object[]{trafficlight, null, null, null};
            outputRowCollector.putValues(resultObj);
        } else {
            for (int i = 0; i < sourceRecords.length(); i++) {
                JSONObject sourceData = sourceRecords.getJSONObject(i).getJSONObject("sourceData");
                matched_gr = sourceData.getString("datasourceRecordId");
                score = Integer.valueOf(sourceRecords.getJSONObject(i).getString("score"));

                if (score >= potTreshold) {
                    trafficlight = "Sure";
                } else {
                    trafficlight = "Unsure";
                }
                resultObj = new Object[]{trafficlight, matched_gr,  score};
                outputRowCollector.putValues(resultObj);
            }

        }
    }


    private void parseGoldenRecordResult(JSONObject recordList) throws JSONException {
        String trafficlight;
        String matched_gr;
        Integer score;
        Object[] resultObj;
        JSONArray goldenrecords = recordList.getJSONArray("goldenRecords");
        if (goldenrecords.length() == 0) {
            trafficlight = "NoMatch";
            resultObj = new Object[]{trafficlight, null, null, null};
            outputRowCollector.putValues(resultObj);
        } else {
            for (int i = 0; i < goldenrecords.length(); i++) {
                JSONObject goldenrecord = goldenrecords.getJSONObject(i).getJSONObject("goldenRecord");
                matched_gr = goldenrecord.getString("goldenRecordId");
                score = Integer.valueOf(goldenrecords.getJSONObject(i).getString("score"));

                if (score >= potTreshold) {
                    trafficlight = "Sure";
                } else {
                    trafficlight = "Unsure";
                }
                resultObj = new Object[]{trafficlight, matched_gr,  score};
                outputRowCollector.putValues(resultObj);
            }
        }
    }


    private class SearchQuery {
        private InputRow inputRow;

        SearchQuery(InputRow inputRow) {
            this.inputRow = inputRow;
        }

        String invoke() {
            String searchParam;
            String inpName = "";
            String gender = "";
            String street = "";
            String postalcode = "";
            String city = "";
            String country = "";
            String birth_date = "";

            for (int i = 0; i < columns.length; i++) {
                String identifyField = mappedFields[i].toString();

                if (Objects.equals(identifyField, "name")) {
                    inpName = "\"name\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }
                if (Objects.equals(identifyField, "hn")) {
                    gender = "\"housenumber\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }
                if (Objects.equals(identifyField, "street")) {
                    street = "\"street\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }

                if (Objects.equals(identifyField, "city")) {
                    city = "     \"city\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }
                if (Objects.equals(identifyField, "postcode")) {
                    postalcode = "     \"postcode\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }
                if (Objects.equals(identifyField, "birth")) {
                    birth_date = "     \"birth\":\"" + inputRow.getValues(columns[i]).get(0) + "\",\n";
                }

            }


            searchParam = inpName + gender + street + postalcode + city + country + birth_date;


            return "{  \n" +
                    "   \"searchParam\":{  \n" +
                    searchParam +
                    "     \"recordCategory\":\"" + recordType.getName() + "\"\n" +
                    "   },\n" +
                    "   \"searchConfigParam\": {\n" +
                    "\t  \"searchScoreThreshold\":\"" + minScore + "\",\n" +
                    "\t  \"isSearchLite\":\"n\",\n" +
                    "\t  \"maxSearchResult\":\"" + maxRecords + "\",\n" +
                    "\t  \"isElasticSearch\":\"false\"\n" +
                    "\t},\n" +
                    "  \"recordTypeList\":[\"" + recordType.getName() + "\"]\n" +
                    "}";
        }
    }
}

