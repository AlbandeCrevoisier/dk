import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class RecoSys {

  public static class TokenizerMapper
      extends Mapper<Object, Text, Text, MapWritable> {

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      ArrayList<Text> items = new ArrayList<Text>();
      while (itr.hasMoreTokens())
        items.add(new Text(itr.nextToken()));
      for (Text k : items) {
        for (Text v : items) {
          if (k != v) {
            MapWritable tmp = new MapWritable();
            tmp.put(v, new IntWritable(1));
            context.write(k, tmp);
          }
        }
      }
    }
  }

  public static class FrequenciesSumReducer
      extends Reducer<Text, MapWritable, Text, MapWritable> {

    public void reduce(Text key, Iterable<MapWritable> freq, Context context)
        throws IOException, InterruptedException {
      MapWritable item_freq = new MapWritable();
      for (MapWritable M : freq) {
        for (Writable keyWritable : M.keySet()) {
          Text item = (Text) keyWritable;
          if (!item_freq.containsKey(item)) {
            item_freq.put(item, M.get(item));
          } else {
            int i = ((IntWritable) item_freq.get(item)).get();
            int j = ((IntWritable) M.get(item)).get();
            item_freq.put(item, new IntWritable(i + j));
          }
        }
      }
      context.write(key, item_freq);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "recommendation system");
    job.setJarByClass(RecoSys.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(FrequenciesSumReducer.class);
    job.setReducerClass(FrequenciesSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(MapWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
