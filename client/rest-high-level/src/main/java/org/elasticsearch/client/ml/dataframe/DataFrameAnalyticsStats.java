/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.client.ml.dataframe;

import org.elasticsearch.client.ml.NodeAttributes;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.inject.internal.ToStringBuilder;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;

import java.io.IOException;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.ConstructingObjectParser.constructorArg;
import static org.elasticsearch.common.xcontent.ConstructingObjectParser.optionalConstructorArg;

public class DataFrameAnalyticsStats {

    public static DataFrameAnalyticsStats fromXContent(XContentParser parser) throws IOException {
        return PARSER.parse(parser, null);
    }

    static final ParseField ID = new ParseField("id");
    static final ParseField STATE = new ParseField("state");
    static final ParseField PROGRESS_PERCENT = new ParseField("progress_percent");
    static final ParseField NODE = new ParseField("node");
    static final ParseField ASSIGNMENT_EXPLANATION = new ParseField("assignment_explanation");

    private static final ConstructingObjectParser<DataFrameAnalyticsStats, Void> PARSER =
        new ConstructingObjectParser<>("data_frame_analytics_stats", true,
            args -> new DataFrameAnalyticsStats(
                (String) args[0],
                (DataFrameAnalyticsState) args[1],
                (Integer) args[2],
                (NodeAttributes) args[3],
                (String) args[4]));

    static {
        PARSER.declareString(constructorArg(), ID);
        PARSER.declareField(constructorArg(), p -> {
            if (p.currentToken() == XContentParser.Token.VALUE_STRING) {
                return DataFrameAnalyticsState.fromString(p.text());
            }
            throw new IllegalArgumentException("Unsupported token [" + p.currentToken() + "]");
        }, STATE, ObjectParser.ValueType.STRING);
        PARSER.declareInt(optionalConstructorArg(), PROGRESS_PERCENT);
        PARSER.declareObject(optionalConstructorArg(), NodeAttributes.PARSER, NODE);
        PARSER.declareString(optionalConstructorArg(), ASSIGNMENT_EXPLANATION);
    }

    private final String id;
    private final DataFrameAnalyticsState state;
    private final Integer progressPercent;
    private final NodeAttributes node;
    private final String assignmentExplanation;

    public DataFrameAnalyticsStats(String id, DataFrameAnalyticsState state, @Nullable Integer progressPercent,
                                   @Nullable NodeAttributes node, @Nullable String assignmentExplanation) {
        this.id = id;
        this.state = state;
        this.progressPercent = progressPercent;
        this.node = node;
        this.assignmentExplanation = assignmentExplanation;
    }

    public String getId() {
        return id;
    }

    public DataFrameAnalyticsState getState() {
        return state;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    public NodeAttributes getNode() {
        return node;
    }

    public String getAssignmentExplanation() {
        return assignmentExplanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataFrameAnalyticsStats other = (DataFrameAnalyticsStats) o;
        return Objects.equals(id, other.id)
            && Objects.equals(state, other.state)
            && Objects.equals(progressPercent, other.progressPercent)
            && Objects.equals(node, other.node)
            && Objects.equals(assignmentExplanation, other.assignmentExplanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, progressPercent, node, assignmentExplanation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(getClass())
            .add("id", id)
            .add("state", state)
            .add("progressPercent", progressPercent)
            .add("node", node)
            .add("assignmentExplanation", assignmentExplanation)
            .toString();
    }
}
