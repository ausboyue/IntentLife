package cn.icheny.intentlife;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : The packaging unit of a target field of the Activity.
 * </pre>
 */
final class TargetField {
    // Field name in "Activity" class.
    String fieldName;
    // Field type in "Activity" class.
    String fieldType;
    // The value of annotation "BindIntentKey"
    String keyName;
    // Whether the interface is implemented: android.os.Parcelable
    boolean isParcelable;
}
