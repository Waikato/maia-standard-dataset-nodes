package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.ml.dataset.DataRow
import maia.ml.dataset.DataStream
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.io.Input
import maia.topology.io.Output

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class SplitRows : Node<NodeConfiguration> {

    @Configurable.Register<SplitRows, NodeConfiguration>(SplitRows::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit): super(block)

    val input by Input<DataStream<*>>()

    val output1 by Output<DataRow>()

    val output2 by Output<DataRow>()

    override suspend fun main() {
        val dataset = input.pull()
        input.close()
        var toggle = true
        for (row in dataset.rowIterator()) {
            if (toggle) output1.push(row) else output2.push(row)
            toggle = !toggle
        }
    }

}
