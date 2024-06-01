package com.nasiat_muhib.classmate.presentation.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedFieldWithPlaceholder
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.domain.state.SignUpUIState
import com.nasiat_muhib.classmate.strings.OTP_DESCRIPTION
import com.nasiat_muhib.classmate.strings.OTP_LABEL
import com.nasiat_muhib.classmate.strings.OTP_PLACEHOLDER
import com.nasiat_muhib.classmate.strings.VERIFY_OTP_BUTTON
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun VerifyOTPScreen(
    signUpViewModel: SignUpViewModel,
    signUpUIState: SignUpUIState
) {


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SmallSpace),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MediumSpace)
    ) {
        Text(
            text = OTP_DESCRIPTION,
            modifier = Modifier.padding(horizontal = MediumSpace),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        CustomOutlinedFieldWithPlaceholder(
            value = signUpUIState.otp,
            labelValue = OTP_LABEL,
            placeholderValue = OTP_PLACEHOLDER,
            onValueChange = {otp ->
                signUpViewModel.onEvent(SignUpUIEvent.OTPChanged(otp))
            },
            errorMessage = signUpUIState.otpError
        )

        CustomElevatedButton(
            text = VERIFY_OTP_BUTTON,
            onClick = {
                signUpViewModel.onEvent(SignUpUIEvent.VerifyOTPButtonClicked)
            }
        )
    }
}
