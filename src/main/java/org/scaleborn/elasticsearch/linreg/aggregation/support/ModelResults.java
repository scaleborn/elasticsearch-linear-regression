/*
 * Copyright (c) 2017 Scaleborn UG, www.scaleborn.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scaleborn.elasticsearch.linreg.aggregation.support;

import java.io.IOException;
import java.util.Arrays;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.scaleborn.linereg.estimation.SlopeCoefficients;

/**
 * Created by mbok on 07.04.17.
 */
public class ModelResults implements Writeable, ToXContent {

  private final double[] coefficients;

  public ModelResults(final SlopeCoefficients slopeCoefficients, final double intercept) {
    final int slopeLen = slopeCoefficients.getCoefficients().length;
    this.coefficients = new double[slopeLen + 1];
    System.arraycopy(slopeCoefficients.getCoefficients(), 0, this.coefficients, 1, slopeLen);
    this.coefficients[0] = intercept;
  }

  public ModelResults(final StreamInput in) throws IOException {
    this.coefficients = in.readDoubleArray();
  }

  @Override
  public void writeTo(final StreamOutput out) throws IOException {
    out.writeDoubleArray(this.coefficients);
  }

  public double[] getCoefficients() {
    return this.coefficients;
  }

  @Override
  public String toString() {
    return "ModelResults{" +
        "coefficients=" + Arrays.toString(this.coefficients) +
        '}';
  }

  @Override
  public XContentBuilder toXContent(final XContentBuilder builder, final Params params)
      throws IOException {
    builder.array("coefficients", this.coefficients);
    return builder;
  }

}
