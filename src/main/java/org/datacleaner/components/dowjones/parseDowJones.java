package org.datacleaner.components.dowjones;

import org.apache.metamodel.query.Query;
import org.apache.metamodel.schema.ColumnType;
import org.datacleaner.api.*;
import org.datacleaner.components.categories.ReferenceDataCategory;
import org.datacleaner.api.OutputDataStream;
import org.datacleaner.components.dowjones.readers.*;
import org.datacleaner.job.output.OutputDataStreamBuilder;
import org.datacleaner.job.output.OutputDataStreams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.datacleaner.components.dowjones.outputDataStreams.*;

@Named("DowJones Parser")
@Alias("DowJones")
@Description("Parse the Dow Jones xml feed")
@Categorized(ReferenceDataCategory.class)


public class parseDowJones implements Transformer, HasOutputDataStreams {


    @Inject
    @Configured("inputURL")
    InputColumn<String> url;

    @Inject
    @Provided
    OutputRowCollector outputRowCollector;


    private OutputRowCollector _countryRowCollector;

    private List<InputColumn<?>> _outputDataStreamColumns;


    @Override
    public OutputColumns getOutputColumns() {
//  The default output will display the date and type of the DowJones List
        String[] columnNames = {"date", "type"};
        Class<?>[] columnTypes = new Class[]{String.class, String.class};
        return new OutputColumns(columnNames, columnTypes);
    }


    @Override
    public Object[] transform(InputRow inputRow) {

        String dj_date = "";
        String type = "";

        String fileURL = inputRow.getValue(url);

        return new Object[]{fileURL, type};
    }

    @Override
    public OutputDataStream[] getOutputDataStreams() {
        final OutputDataStreamBuilder countryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_COUNTRY);


        countryStreamBuilder.withColumn("Code", ColumnType.STRING);
        countryStreamBuilder.withColumn("Name", ColumnType.STRING);
        countryStreamBuilder.withColumn("IsTerritory", ColumnType.STRING);
        countryStreamBuilder.withColumn("ProfileURL", ColumnType.STRING);


        return new OutputDataStream[]{
                countryStreamBuilder.toOutputDataStream()
        };

    }


    @Override
    public void initializeOutputDataStream(OutputDataStream outputDataStream, Query query, OutputRowCollector outputRowCollector) {

    }
}



