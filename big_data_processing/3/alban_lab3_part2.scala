// Databricks notebook source
// MAGIC %md # Part II: Using Spark ML with Scala
// MAGIC 
// MAGIC In the last part of the TP, we will perform some learning tasks using the ML library but this time using Scala.

// COMMAND ----------

// MAGIC %md ## 1. Load dataset

// COMMAND ----------

// Show available datasets from data-bricks

display(dbutils.fs.ls("/databricks-datasets/samples/data/mllib/"))

// COMMAND ----------

// MAGIC %md We will use the `sample_tree_data` dataset.
// MAGIC 
// MAGIC First we load and parse the data.

// COMMAND ----------

import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils

// Load and parse the data file.
val data = sc.textFile("dbfs:/databricks-datasets/samples/data/mllib/sample_tree_data.csv")
val parsedData = data.map { line =>
  val parts = line.split(',').map(_.toDouble)
  (parts(0), Vectors.dense(parts.tail))
}

// COMMAND ----------

// MAGIC %md Next, we need to define the `label` and `features`. 
// MAGIC 
// MAGIC **Note:** `label` and `features` are the default names used in the ML library, you can use other names but then you need to specified them when using a learning algorithm.

// COMMAND ----------

val data_df = sqlContext.createDataFrame(parsedData).toDF("label", "features")
// This is what the data 'looks like' (only first 3 samples shown)
data_df.head(3).foreach(println)

// COMMAND ----------

// MAGIC %md ## 2. Split data into `train` and `test` sets
// MAGIC 
// MAGIC Split the data into training and test sets (30% held out for testing). 
// MAGIC 
// MAGIC Notice that by defining `seed` we ensure that experiments are replicable.

// COMMAND ----------

val splits = data_df.randomSplit(Array(0.7, 0.3), seed=1)
val (trainingData, testData) = (splits(0), splits(1))

// COMMAND ----------

// MAGIC %md ## 3. Train a Decision Tree classifier
// MAGIC 
// MAGIC At first, we will train a 'shallow' tree.
// MAGIC 
// MAGIC Remember that we are using the `ML` implementation.

// COMMAND ----------

import org.apache.spark.ml.classification.DecisionTreeClassifier

val dt = new DecisionTreeClassifier()
             .setMaxDepth(3)
val model = dt.fit(trainingData)

// COMMAND ----------

// MAGIC %md ## 4. Evaluation

// COMMAND ----------

import org.apache.spark.mllib.evaluation.MulticlassMetrics;

val predictions = model.transform(testData);
val predictions_rdd = predictions.select("label", "prediction").as[(Double, Double)].rdd;
val metrics = new MulticlassMetrics(predictions_rdd);

println("Accuracy:");
println(metrics.accuracy);
println("Confusion Matrix:");
println(metrics.confusionMatrix);

// COMMAND ----------

display(model)

// COMMAND ----------

// MAGIC %md ## 5. Visualize the tree model

// COMMAND ----------

// MAGIC %md ## 6. Experimental Evaluation
// MAGIC 
// MAGIC Now, we will use different parameter values to build the Decision Tree model and check how different this affect the results of the evaluation.
// MAGIC 
// MAGIC ### 1. Change Max Depth.
// MAGIC 
// MAGIC Let's change the parameter `setMaxDepth(n)` where n=5 (default), n=10, n=20. Compute the accuracy for each of these depths. Are the results different?
// MAGIC 
// MAGIC ### 2. Change the Node Impurity measure.
// MAGIC 
// MAGIC Set the `maxDepth` to the default value (5) and compare between using "gini" (default) and "entropy" as impurity measure. What is the best impurity measure (for this dataset)?
// MAGIC 
// MAGIC ### 3. Train a Tree Ensemble (Random Forest) and visualize the first two members of the ensemble.
// MAGIC 
// MAGIC Use `import org.apache.spark.ml.classification.RandomForestClassifier`. Train a RandomForest model using the default parameters and compare it against the DecisionTree model (also using the default parameters). How is performance different?
// MAGIC 
// MAGIC You can use `display(model.trees(n))` to visualize the *n*th tree in the ensemble.
// MAGIC 
// MAGIC ### 4. Change the seed value.
// MAGIC 
// MAGIC The seed value is set by default to some number (`getSeed`). Change that number (`setSeed(n)`). What happens to the model/performance? Explain your answer.
// MAGIC 
// MAGIC ### 5. Change the number of trees in the ensemble.
// MAGIC 
// MAGIC What happens in terms of performance if we change the number of trees? Use `setNumTrees(value)` to set 5, 30 and 100 trees.  
// MAGIC 
// MAGIC ### 6. Use Cross-Validation to find the best Decision Tree model and the best Random Forest model
// MAGIC 
// MAGIC You can use `import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}`, see [example](https://spark.apache.org/docs/latest/ml-tuning.html). However, you are free to imlement the cross validation part on your own.
// MAGIC 
// MAGIC Use the following setup:
// MAGIC - Folds = 3
// MAGIC - Decision Tree hyper-parameter grid:
// MAGIC   - maxDepth: 3, 5, 10
// MAGIC   - impurity: "gini", "entropy"
// MAGIC - Random Forest hyper-parameter grid:
// MAGIC   - maxDepth: 3, 5, 10
// MAGIC   - impurity: "gini", "entropy"
// MAGIC   - numTrees: 10, 30, 100
// MAGIC   
// MAGIC What is the best Decision Tree and the best Random Forest Model? Which one would you choose? Justify your answer.
// MAGIC 
// MAGIC ### 7. Use Cross-Validation to find the best Decision Tree model and the best Random Forest model for *"sample_binary_classification_data"*
// MAGIC 
// MAGIC - File location: "dbfs:/databricks-datasets/samples/data/mllib/sample_binary_classification_data.txt"
// MAGIC - You have to change the code to load the dataset and to create the corresponding DataFrame. Hint:`import org.apache.spark.mllib.util.MLUtils.{loadLibSVMFile, convertVectorColumnsToML}`
// MAGIC - Use the same setup as in the previous step.
// MAGIC 
// MAGIC What is the best Decision Tree and the best Random Forest Model? Which one would you choose? Justify your answer.
