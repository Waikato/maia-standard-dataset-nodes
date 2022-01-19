package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.configure.asReconfigureBlock
import maia.ml.dataset.DataRow
import maia.ml.dataset.util.formatString
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.node.base.LockStepTransformer

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
@Node.WithMetadata("Formats a data-row into a string")
class FormatDataRow :
    LockStepTransformer<FormatDataRowConfiguration, DataRow, String> {

    @Configurable.Register<FormatDataRow, FormatDataRowConfiguration>(
        FormatDataRow::class,
        FormatDataRowConfiguration::class
    )
    constructor(block : FormatDataRowConfiguration.() -> Unit = {}) : super(block)

    constructor(config : FormatDataRowConfiguration) : this(config.asReconfigureBlock())

    override suspend fun transformSingle(item : DataRow) : String {
        return item.formatString()
    }

}

class FormatDataRowConfiguration : NodeConfiguration("formatDataRow") {



}
