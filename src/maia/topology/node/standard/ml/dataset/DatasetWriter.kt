package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.ml.dataset.DataRow
import maia.ml.dataset.DataStream
import maia.topology.NodeConfiguration
import maia.topology.node.base.Sink

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class DatasetWriter : Sink<NodeConfiguration, DataStream<DataRow>> {

    @Configurable.Register<DatasetWriter, NodeConfiguration>(DatasetWriter::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit = {}) : super(block)

    override suspend fun consume(item: DataStream<DataRow>) {
        println("DatasetWriter received dataset: $item")
    }

}
