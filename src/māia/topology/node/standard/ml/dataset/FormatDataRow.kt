package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.asReconfigureBlock
import māia.ml.dataset.DataRow
import māia.ml.dataset.util.formatString
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.node.base.LockStepTransformer

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
